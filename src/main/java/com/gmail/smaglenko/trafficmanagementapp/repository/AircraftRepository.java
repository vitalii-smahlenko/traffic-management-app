package com.gmail.smaglenko.trafficmanagementapp.repository;

import com.gmail.smaglenko.trafficmanagementapp.model.Aircraft;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AircraftRepository extends MongoRepository<Aircraft, Long> {
}
