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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public ChargingSession getChargingSessionById(int sessionId) {
        return chargingSessionRepository.findById(sessionId).orElseThrow(
                () -> new IllegalArgumentException("ChargingSession not found")
        );
    }

    public ChargingSession endChargingSession(ChargingSession chargingSession, double endMeterValue, String errorMessage) {
        LocalDateTime endTime = LocalDateTime.now();

        chargingSession.setEndTime(endTime);
        chargingSession.setEndMeterValue(endMeterValue);
        chargingSession.setErrorMessage(errorMessage);

        return chargingSessionRepository.save(chargingSession);
    }

    public List<ChargingSession> getChargingSessionsByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return chargingSessionRepository.findByStartTimeBetween(startTime, endTime);
    }

    public List<ChargingSession> getChargingSessionsFromDate(LocalDateTime startTime) {
        return chargingSessionRepository.findByStartTimeAfter(startTime);
    }

    public List<ChargingSession> getChargingSessionsUntilDate(LocalDateTime endTime) {
        return chargingSessionRepository.findByStartTimeBefore(endTime);
    }

    public List<ChargingSession> getAllChargingSession() {
        Iterable<ChargingSession> iterable = chargingSessionRepository.findAll();
        List<ChargingSession> chargingSessions = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        return chargingSessions;
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
