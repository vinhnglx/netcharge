package com.vinhnguyen.netChargeNZ.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartChargingSessionRequest {
    @NotNull
    private int connectorId;

    @NotNull
    private int vehicleId;

    @NotNull
    private double startMeterValue;
}
