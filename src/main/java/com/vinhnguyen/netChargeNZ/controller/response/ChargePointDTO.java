package com.vinhnguyen.netChargeNZ.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargePointDTO {
    private int id;
    private String name;
    private String serialNumber;
    private UserDTO owner;
}
