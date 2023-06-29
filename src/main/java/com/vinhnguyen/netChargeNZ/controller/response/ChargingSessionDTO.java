package com.vinhnguyen.netChargeNZ.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargingSessionDTO {
    private int id;
    private String startTime;
    private String endTime;
    private double startMeterValue;
    private double endMeterValue;
    private String errorMessage;
}
