package com.vinhnguyen.netChargeNZ.controller;

import com.vinhnguyen.netChargeNZ.controller.response.VersionDTO;
import com.vinhnguyen.netChargeNZ.service.VersionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @GetMapping("/version")
    public ResponseEntity<VersionDTO> getVersionInfo() {
        VersionDTO versionResponse = new VersionDTO();
        versionResponse.setDatabaseSchemaVersion(versionService.getDatabaseSchemaVersion());
        versionResponse.setApplicationVersion(versionService.getApplicationVersion());
        return ResponseEntity.ok(versionResponse);
    }
}
