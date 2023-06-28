package com.vinhnguyen.netChargeNZ.controller.response;

import com.vinhnguyen.netChargeNZ.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String username;
    private Role role;
}