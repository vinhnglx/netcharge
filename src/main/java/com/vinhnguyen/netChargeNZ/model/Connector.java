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

    @Column(name = "connector_number")
    private int connectorNumber;

    @ManyToOne
    @JoinColumn(name = "charge_point_id", nullable = false)
    private ChargePoint chargePoint;
}

