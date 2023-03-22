package com.gmail.smaglenko.trafficmanagementapp.service.impl;

import com.gmail.smaglenko.trafficmanagementapp.model.Aircraft;
import com.gmail.smaglenko.trafficmanagementapp.repository.AircraftRepository;
import com.gmail.smaglenko.trafficmanagementapp.service.AircraftService;
import com.gmail.smaglenko.trafficmanagementapp.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.gmail.smaglenko.trafficmanagementapp.model.Aircraft.AIRCRAFT_SEQUENCE;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {
    private final AircraftRepository repository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Aircraft add(Aircraft aircraft) {
        aircraft.setId(sequenceGeneratorService.getSequenceNumber(AIRCRAFT_SEQUENCE));
        return repository.save(aircraft);
    }
}
