package com.vinhnguyen.netChargeNZ.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargePointDTO {
    private int id;
    private String name;
    private String serialNumber;
    private UserDTO owner;
    private List<ConnectorDTOLight> connectors;
}
