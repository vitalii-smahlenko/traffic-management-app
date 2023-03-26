package com.gmail.smaglenko.trafficmanagementapp.service.impl;

import com.gmail.smaglenko.trafficmanagementapp.model.AirplaneCharacteristics;
import com.gmail.smaglenko.trafficmanagementapp.model.TemporaryPoint;
import com.gmail.smaglenko.trafficmanagementapp.model.WayPoint;
import com.gmail.smaglenko.trafficmanagementapp.service.PlaneCalculation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PlaneCalculationImpl implements PlaneCalculation {
    private static final double EARTH_RADIUS = 6371.0; // Радіус Землі в км

    public List<TemporaryPoint> calculateRoute(AirplaneCharacteristics characteristics,
                                               List<WayPoint> wayPoints) {
        List<TemporaryPoint> temporaryPoints = new ArrayList<>();
        TemporaryPoint currentPosition = new TemporaryPoint(wayPoints.get(0).getLatitude(),
                wayPoints.get(0).getLongitude(), wayPoints.get(0).getAltitude(), 0, 0);
        double currentSpeed = 0;
        double currentCourse = 0;
        for (int i = 1; i < wayPoints.size(); i++) {
            WayPoint currentWayPoint = wayPoints.get(i);
            WayPoint previousWayPoint = wayPoints.get(i - 1);
            double distance = calculateDistance(currentPosition.getLatitude(),
                    currentPosition.getLongitude(), currentWayPoint.getLatitude(),
                    currentWayPoint.getLongitude());
            double requiredAltitude = currentWayPoint.getAltitude();
            double requiredSpeed = currentWayPoint.getSpeed();
            double requiredCourseRadians = calculateCourse(currentPosition.getLatitude(),
                    currentPosition.getLongitude(), currentWayPoint.getLatitude(),
                    currentWayPoint.getLongitude());
            currentCourse = requiredCourseRadians;
            double acceleration = characteristics.getMaxAcceleration();
            double maxSpeed = characteristics.getMaxSpeed();
            double maxPossibleSpeed = Math.sqrt(currentSpeed * currentSpeed + 2 * acceleration
                    * distance);
            if (currentSpeed == 0 || requiredSpeed == 0
                    || requiredAltitude != currentPosition.getAltitude()) {
                // Якщо швидкість нуль або швидкість або висота не співпадають,
                // потрібно прискоритися до вимог маршруту
                currentSpeed = requiredSpeed;
            } else if (requiredSpeed > currentSpeed) {
                // Якщо потрібна більша швидкість, прискорити до неї
                double maxAchievableSpeed = Math.min(maxSpeed,
                        Math.sqrt(currentSpeed * currentSpeed + 2 * acceleration * distance));
                currentSpeed = Math.min(maxAchievableSpeed, requiredSpeed);
            } else {
                // Якщо потрібна менша швидкість, сповільнити до неї
                double minAchievableSpeed
                        = Math.sqrt(currentSpeed * currentSpeed - 2 * acceleration * distance);
                currentSpeed = Math.max(minAchievableSpeed, requiredSpeed);
            }
            double time = distance / currentSpeed;
            double currentAltitude = currentPosition.getAltitude();
            double altitudeChange = requiredAltitude - currentAltitude;
            double altitudeSpeed = characteristics.getMaxSpeed();
            currentPosition = move(currentPosition, requiredCourseRadians, currentSpeed,
                    altitudeSpeed, altitudeChange, time);
            currentPosition.setSpeed(currentSpeed);
            currentPosition.setCourse(currentCourse);
            temporaryPoints.add(currentPosition);
        }
        return temporaryPoints;
    }

    public double durationFlight(List<WayPoint> wayPoints) {
        double totalFlightTime = 0;
        for (int i = 1; i < wayPoints.size(); i++) {
            WayPoint currentWayPoint = wayPoints.get(i);
            WayPoint previousWayPoint = wayPoints.get(i - 1);
            double distance = calculateDistance(previousWayPoint.getLatitude(),
                    previousWayPoint.getLongitude(), currentWayPoint.getLatitude(),
                    currentWayPoint.getLongitude());
            double time = distance / (currentWayPoint.getSpeed() / 60.0);
            totalFlightTime += time;
        }
        return totalFlightTime;
    }


    private double calculateDistance(double startLat, double startLon, double endLat,
                                     double endLon) {
        // Переведення координат у радіани
        double lat1 = Math.toRadians(startLat);
        double lon1 = Math.toRadians(startLon);
        double lat2 = Math.toRadians(endLat);
        double lon2 = Math.toRadians(endLon);
        // Обчислення різниці координат
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        // Обчислення відстані між точками за формулою гаверсинусів
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    private double calculateCourse(double startLat, double startLon, double endLat, double endLon) {
        // Переведення координат у радіани
        double lat1 = Math.toRadians(startLat);
        double lon1 = Math.toRadians(startLon);
        double lat2 = Math.toRadians(endLat);
        double lon2 = Math.toRadians(endLon);
        // Обчислення різниці координат
        double dLon = lon2 - lon1;
        // Обчислення курсу
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) -
                Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double course = Math.toDegrees(Math.atan2(y, x));
        course = (course + 360) % 360;
        return course;
    }

    private TemporaryPoint move(TemporaryPoint currentPosition, double course, double speed,
                                double altitudeSpeed, double altitudeChange, double timeInterval) {
        // Переведення курсу в радіани
        double radianCourse = Math.toRadians(course);
        // Обчислення зміни висоти за час timeInterval
        double altitudeChangePerInterval = altitudeSpeed * timeInterval;
        double newHeight = currentPosition.getAltitude() + altitudeChange;
        double climbRate = altitudeChange / timeInterval;
        // Обчислення переміщення вздовж широти та довготи
        double distance = speed * timeInterval;
        double lat1 = Math.toRadians(currentPosition.getLatitude());
        double lon1 = Math.toRadians(currentPosition.getLongitude());
        double angularDistance = distance / EARTH_RADIUS;
        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(angularDistance) +
                Math.cos(lat1) * Math.sin(angularDistance) * Math.cos(radianCourse));
        double lon2 = lon1 + Math.atan2(Math.sin(radianCourse) * Math.sin(angularDistance)
                        * Math.cos(lat1),
                Math.cos(angularDistance) - Math.sin(lat1) * Math.sin(lat2));
        // Переведення координат назад у градуси
        double newLatitude = Math.toDegrees(lat2);
        double newLongitude = Math.toDegrees(lon2);
        return new TemporaryPoint(newLatitude, newLongitude, newHeight, climbRate, speed);
    }
}
