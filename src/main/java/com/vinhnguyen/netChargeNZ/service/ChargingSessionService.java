package com.vinhnguyen.netChargeNZ.service;

import com.vinhnguyen.netChargeNZ.model.ChargingSession;
import com.vinhnguyen.netChargeNZ.model.Connector;
import com.vinhnguyen.netChargeNZ.model.Vehicle;
import com.vinhnguyen.netChargeNZ.repository.ChargingSessionRepository;
import com.vinhnguyen.netChargeNZ.repository.ConnectorRepository;
import com.vinhnguyen.netChargeNZ.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ChargingSessionService {

    private final ChargingSessionRepository chargingSessionRepository;
    private final ConnectorRepository connectorRepository;
    private final VehicleRepository vehicleRepository;
    public ChargingSession startChargingSession(Connector connector, Vehicle vehicle, double startMeterValue) {
        LocalDateTime startTime = LocalDateTime.now();

        ChargingSession chargingSession = ChargingSession
                .builder()
                .startTime(startTime)
                .connector(connector)
                .vehicle(vehicle)
                .startMeterValue(startMeterValue)
                .build();

        return chargingSessionRepository.save(chargingSession);
    }

    public boolean existsByConnectorAndVehicleAndEndTimeIsNull(Connector connector, Vehicle vehicle) {
        return chargingSessionRepository
                .existsByConnectorAndVehicleAndEndTimeIsNull(
                        connector.getId(), vehicle.getId());
    }

    public Connector getConnectorById(int connectorId) {
        return connectorRepository.findById(connectorId).orElseThrow(
                () -> new IllegalArgumentException("Connector not found")
        );
    }

    public Vehicle getVehicleById(int vehicleId) {
        return vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new IllegalArgumentException("Vehicle not found")
        );
    }
}