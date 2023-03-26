package com.gmail.smaglenko.trafficmanagementapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneCharacteristics {
    private double maxSpeed;
    private double maxAcceleration;
    private double climbRate;
    private double turnRate;
}
