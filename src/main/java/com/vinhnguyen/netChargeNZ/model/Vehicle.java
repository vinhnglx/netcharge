package com.vinhnguyen.netChargeNZ.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String registrationPlate;

    @OneToOne
    private RFIDTag rfidTag;
}