package com.gmail.smaglenko.trafficmanagementapp.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private Long number;
    private List<WayPoint> wayPoints = new ArrayList<>();
    private List<TemporaryPoint> temporaryPoints = new ArrayList<>();
}
