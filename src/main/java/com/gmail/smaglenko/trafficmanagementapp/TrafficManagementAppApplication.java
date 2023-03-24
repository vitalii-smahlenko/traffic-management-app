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
            Aircraft airplane1 = new Airplane();

            AirplaneCharacteristics airplane1Characteristics = new AirplaneCharacteristics();
            airplane1Characteristics.setTurnRate(200);
            airplane1Characteristics.setMaxSpeed(150);
            airplane1Characteristics.setMaxAcceleration(100);
            airplane1Characteristics.setClimbRate(10);

            WayPoint firstWayPointForAirplane1 = new WayPoint();
            firstWayPointForAirplane1.setLatitude(30.514612);
            firstWayPointForAirplane1.setLongitude(50.530600);
            firstWayPointForAirplane1.setHeight(1000);
            firstWayPointForAirplane1.setSpeed(120);

            WayPoint secondWayPointForAirplane1 = new WayPoint();
            secondWayPointForAirplane1.setLatitude(30.514605);
            secondWayPointForAirplane1.setLongitude(50.515705);
            secondWayPointForAirplane1.setHeight(1000);
            secondWayPointForAirplane1.setSpeed(120);

            WayPoint thirdWayPointForAirplane1 = new WayPoint();
            thirdWayPointForAirplane1.setLatitude(30.500160);
            thirdWayPointForAirplane1.setLongitude(50.516652);
            thirdWayPointForAirplane1.setHeight(1000);
            thirdWayPointForAirplane1.setSpeed(120);

            List<WayPoint> wayPointsForAirplane1 = new ArrayList<>();
            wayPointsForAirplane1.add()

            planeCalculation.calculateRoute()

            airplane1.setCharacteristics();
            airplane1.setPosition();
            airplane1.getFlights();
        };
    }

}
