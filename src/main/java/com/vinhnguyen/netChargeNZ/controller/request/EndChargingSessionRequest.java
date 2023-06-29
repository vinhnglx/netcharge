package com.vinhnguyen.netChargeNZ.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndChargingSessionRequest {
    @NotNull
    private double endMeterValue;
}
