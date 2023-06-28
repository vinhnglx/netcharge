package com.vinhnguyen.netChargeNZ.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class VersionService {
    @Value("${app.version}")
    private String applicationVersion;

    @Value("${db.schema.version}")
    private String databaseSchemaVersion;
}
