package com.gmail.smaglenko.trafficmanagementapp.service;

import com.gmail.smaglenko.trafficmanagementapp.model.AirplaneCharacteristics;
import com.gmail.smaglenko.trafficmanagementapp.model.TemporaryPoint;
import com.gmail.smaglenko.trafficmanagementapp.model.WayPoint;
import java.util.List;

public interface PlaneCalculation {
    List<TemporaryPoint> calculateRoute(AirplaneCharacteristics characteristics,
                                        List<WayPoint> wayPoints);
}
