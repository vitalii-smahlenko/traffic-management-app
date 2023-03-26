package com.gmail.smaglenko.trafficmanagementapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "db_sequence")
public class DbSequence {
    @Id
    private String id;
    private Long seq;
}
