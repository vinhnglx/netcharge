package com.vinhnguyen.netChargeNZ.controller;

import com.vinhnguyen.netChargeNZ.controller.response.VersionResponse;
import com.vinhnguyen.netChargeNZ.service.VersionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @GetMapping("/version")
    public ResponseEntity<VersionResponse> getVersionInfo() {
        VersionResponse versionResponse = new VersionResponse();
        versionResponse.setDatabaseSchemaVersion(versionService.getDatabaseSchemaVersion());
        versionResponse.setApplicationVersion(versionService.getApplicationVersion());
        return ResponseEntity.ok(versionResponse);
    }
}
