package com.vinhnguyen.netChargeNZ.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateConnectorRequest {
    @NotNull
    private int connectorNumber;
    @NotNull
    private int chargePointId;
}
