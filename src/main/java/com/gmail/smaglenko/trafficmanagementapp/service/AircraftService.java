package com.gmail.smaglenko.trafficmanagementapp.service;

import com.gmail.smaglenko.trafficmanagementapp.model.Aircraft;
import java.util.List;

public interface AircraftService {
    Aircraft add(Aircraft aircraft);

    Aircraft update(Aircraft aircraft);

    List<Aircraft> findAll();
}
