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

        };
    }

}
