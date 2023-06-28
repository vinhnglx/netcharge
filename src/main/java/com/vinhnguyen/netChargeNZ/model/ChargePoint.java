package com.vinhnguyen.netChargeNZ.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChargePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(unique = true)
    private String serialNumber;

    @ManyToOne
    private User owner;
}