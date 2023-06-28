package com.vinhnguyen.netChargeNZ.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateConnectorRequest {
    @NotNull
    private int connector_number;
    @NotNull
    private int charge_point_id;
}
