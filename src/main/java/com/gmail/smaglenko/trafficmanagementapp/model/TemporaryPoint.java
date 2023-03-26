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
    private double altitude;
    private double speed;
    private double course;
}
