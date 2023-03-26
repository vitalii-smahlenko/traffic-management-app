package com.gmail.smaglenko.trafficmanagementapp;

import com.gmail.smaglenko.trafficmanagementapp.model.Aircraft;
import com.gmail.smaglenko.trafficmanagementapp.model.Airplane;
import com.gmail.smaglenko.trafficmanagementapp.model.AirplaneCharacteristics;
import com.gmail.smaglenko.trafficmanagementapp.model.Flight;
import com.gmail.smaglenko.trafficmanagementapp.model.TemporaryPoint;
import com.gmail.smaglenko.trafficmanagementapp.model.WayPoint;
import com.gmail.smaglenko.trafficmanagementapp.service.AircraftService;
import com.gmail.smaglenko.trafficmanagementapp.service.PlaneCalculation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TrafficManagementAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrafficManagementAppApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AircraftService aircraftService, PlaneCalculation planeCalculation) {
        return args -> {
            for (int i = 0; i < 3; i++) {
                Aircraft airplane = new Airplane();
                AirplaneCharacteristics airplane1Characteristics = new AirplaneCharacteristics
                        (200, 150, 100, 10);
                airplane.setCharacteristics(airplane1Characteristics);
                aircraftService.add(airplane);
            }
        };
    }

    @Bean
    CommandLineRunner start(AircraftService aircraftService, PlaneCalculation planeCalculation) {
        return args -> {
            List<Aircraft> aircrafts = aircraftService.findAll();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < aircrafts.size(); j++) {
                    WayPoint firstWayPoint
                            = new WayPoint(30.514612 + j, 50.530600 + j,
                            1000, 120);
                    WayPoint secondWayPoint
                            = new WayPoint(30.514605 + j, 50.515705 + j,
                            1000, 120);
                    WayPoint thirdWayPoint
                            = new WayPoint(30.500160 + j, 50.516652 + j,
                            1000, 120);
                    List<WayPoint> wayPoints = new ArrayList<>();
                    wayPoints.add(firstWayPoint);
                    wayPoints.add(secondWayPoint);
                    wayPoints.add(thirdWayPoint);
                    List<TemporaryPoint> temporaryPoints = planeCalculation
                            .calculateRoute(aircrafts.get(j).getCharacteristics(), wayPoints);
                    Flight flight = new Flight();
                    flight.setNumber((long) i + 1);
                    flight.setWayPoints(wayPoints);
                    flight.setTemporaryPoints(temporaryPoints);
                    flight.setDurationFlight(planeCalculation.durationFlight(wayPoints));
                    aircrafts.get(j).getFlights().add(flight);
                    Aircraft update = aircraftService.update(aircrafts.get(j));
                    double durationFlight = update.getFlights().stream()
                            .mapToDouble(Flight::getDurationFlight)
                            .sum();
                    System.out.format("%d plain. Number of flights performed %d, "
                                    + "duration of flight time %.19f minutes.\n",
                            j + 1, aircrafts.get(j).getFlights().size(), durationFlight);
                }
            }
        };
    }
}
