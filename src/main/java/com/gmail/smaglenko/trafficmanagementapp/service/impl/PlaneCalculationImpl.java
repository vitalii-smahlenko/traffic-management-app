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
    public List<TemporaryPoint> calculateRoute(AirplaneCharacteristics characteristics,
                                               List<WayPoint> wayPoints) {
        // Ініціалізація тимчасових змінних
        List<TemporaryPoint> temporaryPoints = new ArrayList<>();
        TemporaryPoint currentPosition = new TemporaryPoint(wayPoints.get(0).getLatitude(),
                wayPoints.get(0).getLongitude(), wayPoints.get(0).getHeight(), 0, 0);
        double currentSpeed = 0;
        double currentCourse = 0;
        double timeInterval = 1;
        double earthRadius = 6371.0; // Радіус Землі в км
        // Обробка кожної точки маршруту
        for (int i = 1; i < wayPoints.size(); i++) {
            WayPoint currentWayPoint = wayPoints.get(i);
            WayPoint previousWayPoint = wayPoints.get(i - 1);
            double distance = calculateDistance(currentPosition.getLatitude(),
                    currentPosition.getLongitude(), currentWayPoint.getLatitude(),
                    currentWayPoint.getLongitude(), earthRadius);
            double time = distance / currentSpeed;
            double requiredAltitude = currentWayPoint.getHeight();
            double requiredSpeed = currentWayPoint.getSpeed();
            double requiredCourse = calculateCourse(currentPosition.getLatitude(),
                    currentPosition.getLongitude(), currentWayPoint.getLatitude(),
                    currentWayPoint.getLongitude());
            double acceleration = characteristics.getMaxAcceleration();
            double maxSpeed = characteristics.getMaxSpeed();
            // Обчислення максимальної швидкості, яку може розвинути літак на цій ділянці маршруту
            double maxPossibleSpeed = Math.min(maxSpeed, Math.sqrt(currentSpeed * currentSpeed
                    + 2 * acceleration * distance));
            // Перевірка можливості розвинути необхідну швидкість на даній ділянці маршруту
            if (requiredSpeed > maxPossibleSpeed) {
                // Якщо швидкість недостатня, потрібно прискоритися
                while (currentSpeed < requiredSpeed) {
                    currentSpeed += acceleration * timeInterval;
                    currentSpeed = Math.min(currentSpeed, maxPossibleSpeed);
                    double currentAltitude = currentPosition.getHeight();
                    double altitudeChange = requiredAltitude - currentAltitude;
                    double altitudeSpeed = characteristics.getMaxSpeed();
                    currentPosition = move(currentPosition, requiredCourse, currentSpeed,
                            altitudeSpeed, altitudeChange, timeInterval, earthRadius);
                    temporaryPoints.add(currentPosition);
                }
            } else {
                // Якщо швидкість достатня, потрібно підтримувати її
                currentSpeed = requiredSpeed;
                double currentAltitude = currentPosition.getHeight();
                double altitudeChange = requiredAltitude - currentAltitude;
                double altitudeSpeed = characteristics.getMaxSpeed();
                currentPosition = move(currentPosition, requiredCourse, currentSpeed, altitudeSpeed,
                        altitudeChange, time, earthRadius);
                temporaryPoints.add(currentPosition);
            }
        }
        return temporaryPoints;
    }

    private double calculateDistance(double startLat, double startLon, double endLat, double endLon,
                                     double earthRadius) {
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
        return earthRadius * c;
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
                                double altitudeSpeed, double altitudeChange,
                                double timeInterval, double earthRadius) {
        // Переведення курсу в радіани
        double radianCourse = Math.toRadians(course);
        // Обчислення зміни висоти за час timeInterval
        double altitudeChangePerInterval = altitudeSpeed * timeInterval;
        double newHeight = currentPosition.getHeight() + altitudeChange;
        double climbRate = altitudeChange / timeInterval;
        // Обчислення переміщення вздовж широти та довготи
        double distance = speed * timeInterval;
        double lat1 = Math.toRadians(currentPosition.getLatitude());
        double lon1 = Math.toRadians(currentPosition.getLongitude());
        double angularDistance = distance / earthRadius;
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
