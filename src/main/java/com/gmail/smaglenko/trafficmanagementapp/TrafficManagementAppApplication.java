package com.gmail.smaglenko.trafficmanagementapp;

import com.gmail.smaglenko.trafficmanagementapp.model.Aircraft;
import com.gmail.smaglenko.trafficmanagementapp.model.Airplane;
import com.gmail.smaglenko.trafficmanagementapp.model.AirplaneCharacteristics;
import com.gmail.smaglenko.trafficmanagementapp.model.Flight;
import com.gmail.smaglenko.trafficmanagementapp.model.TemporaryPoint;
import com.gmail.smaglenko.trafficmanagementapp.model.WayPoint;
import com.gmail.smaglenko.trafficmanagementapp.service.AircraftService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TrafficManagementAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrafficManagementAppApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AircraftService aircraftService) {
        return args -> {
            AirplaneCharacteristics characteristics = new AirplaneCharacteristics();
            characteristics.setMaxSpeed(100.00);
            characteristics.setMaxAcceleration(2);
            characteristics.setClimbRate(50);
            characteristics.setTurnRate(30);

            TemporaryPoint temporaryPoint = new TemporaryPoint();
            temporaryPoint.setLatitude(55.755831);
            temporaryPoint.setLongitude(37.617673);
            temporaryPoint.setAltitude(3000);
            temporaryPoint.setSpeed(120);
            temporaryPoint.setHeading(20);

            WayPoint wayPoint = new WayPoint();
            wayPoint.setLatitude(50.155820);
            wayPoint.setLongitude(30.516693);
            wayPoint.setAltitude(3500);
            wayPoint.setSpeed(70);

            Flight flight = new Flight();
            flight.setNumber(1L);
            flight.getTemporaryPoints().add(temporaryPoint);
            flight.getWayPoints().add(wayPoint);

            TemporaryPoint temporaryPointForAirplane = new TemporaryPoint();
            temporaryPointForAirplane.setLatitude(53.777777);
            temporaryPointForAirplane.setLongitude(35.555555);
            temporaryPointForAirplane.setAltitude(1000);
            temporaryPointForAirplane.setSpeed(200);
            temporaryPointForAirplane.setHeading(10);

            Aircraft airplane = new Airplane();
            airplane.setCharacteristics(characteristics);
            airplane.setPosition(temporaryPointForAirplane);
            airplane.getFlights().add(flight);

            aircraftService.add(airplane);
        };
    }

}
