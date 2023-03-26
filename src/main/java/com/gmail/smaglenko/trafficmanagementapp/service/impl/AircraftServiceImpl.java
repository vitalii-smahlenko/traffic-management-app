package com.gmail.smaglenko.trafficmanagementapp.service.impl;

import com.gmail.smaglenko.trafficmanagementapp.model.Aircraft;
import com.gmail.smaglenko.trafficmanagementapp.repository.AircraftRepository;
import com.gmail.smaglenko.trafficmanagementapp.service.AircraftService;
import com.gmail.smaglenko.trafficmanagementapp.service.SequenceGeneratorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.gmail.smaglenko.trafficmanagementapp.model.Aircraft.AIRCRAFT_SEQUENCE;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Aircraft update(Aircraft aircraft) {
        Aircraft aircraftFromDb = repository.findById(aircraft.getId()).orElseThrow(
                () -> new RuntimeException("Can't find aircraft " + aircraft)
        );
        aircraftFromDb.setPosition(aircraft.getPosition());
        aircraftFromDb.setFlights(aircraft.getFlights());
        return repository.save(aircraftFromDb);
    }

    @Override
    public List<Aircraft> findAll() {
        return repository.findAll();
    }
}
