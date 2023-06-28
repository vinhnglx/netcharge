package com.vinhnguyen.netChargeNZ.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VersionDTO {
    private String applicationVersion;
    private String databaseSchemaVersion;
}
