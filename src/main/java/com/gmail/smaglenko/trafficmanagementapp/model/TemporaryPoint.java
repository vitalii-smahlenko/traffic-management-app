package com.gmail.smaglenko.trafficmanagementapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryPoint {
    private double latitude;
    private double longitude;
    private int altitude;
    private int speed;
    private int heading;
}
