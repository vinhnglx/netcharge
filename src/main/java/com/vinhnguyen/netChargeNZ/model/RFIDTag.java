package com.vinhnguyen.netChargeNZ.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RFIDTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @Column(unique = true)
    private String number;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
}
