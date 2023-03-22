package com.gmail.smaglenko.trafficmanagementapp.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "aircraft")
public abstract class Aircraft {
    @Transient
    public static final String AIRCRAFT_SEQUENCE = "aircraft_sequence";
    @Id
    private Long id;
    private AirplaneCharacteristics characteristics;
    private TemporaryPoint position;
    private List<Flight> flights = new ArrayList<>();
}
