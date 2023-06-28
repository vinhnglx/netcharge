package com.vinhnguyen.netChargeNZ.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChargePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @Column(unique = true, name = "serial_number")
    @NotNull
    private String serialNumber;

    @ManyToOne
    @JoinColumn(nullable = false, name = "owner_id")
    private User owner;
}