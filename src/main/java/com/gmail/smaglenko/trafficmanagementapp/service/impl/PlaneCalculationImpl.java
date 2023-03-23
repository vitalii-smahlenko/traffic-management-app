package com.gmail.smaglenko.trafficmanagementapp.service.impl;

import com.gmail.smaglenko.trafficmanagementapp.model.AirplaneCharacteristics;
import com.gmail.smaglenko.trafficmanagementapp.model.TemporaryPoint;
import com.gmail.smaglenko.trafficmanagementapp.model.WayPoint;
import com.gmail.smaglenko.trafficmanagementapp.service.PlaneCalculation;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaneCalculationImpl implements PlaneCalculation {

    @Override
    public List<TemporaryPoint> calculateRoute(AirplaneCharacteristics characteristics,
                                               List<WayPoint> wayPoints) {
        List<TemporaryPoint> temporaryPoints = new ArrayList<>();

        return temporaryPoints;
    }
}