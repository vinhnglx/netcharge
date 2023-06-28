package com.vinhnguyen.netChargeNZ.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Connector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int connectorNumber;

    @ManyToOne
    private ChargePoint chargePoint;
}

