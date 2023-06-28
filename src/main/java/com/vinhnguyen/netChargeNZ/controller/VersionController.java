package com.vinhnguyen.netChargeNZ.controller;

import com.vinhnguyen.netChargeNZ.service.VersionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Map<String, String>> getVersionInfo() {
        Map<String, String> versionInfo = new HashMap<>();
        versionInfo.put("applicationVersion", versionService.getApplicationVersion());
        versionInfo.put("databaseSchemaVersion", versionService.getDatabaseSchemaVersion());
        return ResponseEntity.ok(versionInfo);
    }
}
