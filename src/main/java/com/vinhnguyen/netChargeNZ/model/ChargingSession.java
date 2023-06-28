package com.vinhnguyen.netChargeNZ.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double startMeterValue;

    private double endMeterValue;

    @ManyToOne
    private Connector connector;

    @ManyToOne
    private Vehicle vehicle;
}

