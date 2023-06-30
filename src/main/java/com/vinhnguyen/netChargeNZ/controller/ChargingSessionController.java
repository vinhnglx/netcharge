package com.vinhnguyen.netChargeNZ.controller;

import com.vinhnguyen.netChargeNZ.controller.request.EndChargingSessionRequest;
import com.vinhnguyen.netChargeNZ.controller.request.StartChargingSessionRequest;
import com.vinhnguyen.netChargeNZ.controller.response.ChargingSessionDTO;
import com.vinhnguyen.netChargeNZ.model.ChargingSession;
import com.vinhnguyen.netChargeNZ.model.Connector;
import com.vinhnguyen.netChargeNZ.model.Vehicle;
import com.vinhnguyen.netChargeNZ.service.ChargingSessionService;
import com.vinhnguyen.netChargeNZ.util.DateConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ChargingSessionController {

    private final ChargingSessionService chargingSessionService;

    @GetMapping("/chargingSessions")
    public ResponseEntity<?> getChargingSessions(
        @RequestParam(required = false) String startTime,
        @RequestParam(required = false) String endTime
    ) {
        DateConverter dateConverter = new DateConverter();

        try {
            LocalDateTime formattedStartTime = startTime != null ? dateConverter.convertStringToDateTime(startTime) : null;
            LocalDateTime formattedEndTime = endTime != null ? dateConverter.convertStringToDateTime(endTime) : null;

            List<ChargingSession> chargingSessions;

            if (startTime != null && endTime != null) {
                chargingSessions = chargingSessionService
                        .getChargingSessionsByDateRange(formattedStartTime, formattedEndTime);
            } else if (startTime != null) {
                chargingSessions = chargingSessionService.getChargingSessionsFromDate(formattedStartTime);
            } else if (endTime != null) {
                chargingSessions = chargingSessionService.getChargingSessionsUntilDate(formattedEndTime);
            } else {
                chargingSessions = chargingSessionService.getAllChargingSession();
            }

            List<ChargingSessionDTO> chargingSessionDTOs = chargingSessions.stream()
                    .map(this::buildChargingSessionDTO)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(chargingSessionDTOs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }

    @PostMapping("/chargingSessions")
    public ResponseEntity<?> startChargingSession(@RequestBody StartChargingSessionRequest request) {
        int connectorId = request.getConnectorId();
        int vehicleId = request.getVehicleId();
        double startMeterValue = request.getStartMeterValue();

        try {
            Connector connector = chargingSessionService.getConnectorById(connectorId);
            Vehicle vehicle = chargingSessionService.getVehicleById(vehicleId);

            if (chargingSessionService.existsByConnectorAndVehicleAndEndTimeIsNull(connector, vehicle)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("" +
                        "A charging session is already active for the specified" +
                        " connector and vehicle");
            }

            ChargingSession chargingSession = chargingSessionService
                    .startChargingSession(connector, vehicle, startMeterValue);

            DateConverter dateConverter = new DateConverter();

            ChargingSessionDTO chargingSessionDTO = new ChargingSessionDTO();
            chargingSessionDTO.setId(chargingSession.getId());
            chargingSessionDTO.setStartTime(dateConverter.convertDateTimeToString(chargingSession.getStartTime()));
            chargingSessionDTO.setStartMeterValue(chargingSession.getStartMeterValue());

            return ResponseEntity.status(HttpStatus.OK).body(chargingSessionDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }

    @PutMapping("/chargingSessions/{sessionId}/end")
    public ResponseEntity<?> endChargingSession(
            @PathVariable int sessionId,
            @RequestBody EndChargingSessionRequest request,
            @RequestParam(required = false) String errorMessage) {
        double endMeterValue = request.getEndMeterValue();

        try {
            ChargingSession chargingSession = chargingSessionService.getChargingSessionById(sessionId);

            if (chargingSession.getEndTime() != null) {
                throw new IllegalStateException("Charging session has already ended");
            }

            chargingSession = chargingSessionService.endChargingSession(chargingSession, endMeterValue, errorMessage);

            ChargingSessionDTO chargingSessionDTO = buildChargingSessionDTO(chargingSession);

            return ResponseEntity.status(HttpStatus.OK).body(chargingSessionDTO);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }

    private ChargingSessionDTO buildChargingSessionDTO(ChargingSession chargingSession) {
        DateConverter dateConverter = new DateConverter();

        ChargingSessionDTO chargingSessionDTO = new ChargingSessionDTO();
        chargingSessionDTO.setId(chargingSession.getId());
        chargingSessionDTO.setStartTime(dateConverter.convertDateTimeToString(chargingSession.getStartTime()));
        chargingSessionDTO.setEndTime(dateConverter.convertDateTimeToString(chargingSession.getEndTime()));
        chargingSessionDTO.setStartMeterValue(chargingSession.getStartMeterValue());
        chargingSessionDTO.setEndMeterValue(chargingSession.getEndMeterValue());
        chargingSessionDTO.setErrorMessage(chargingSession.getErrorMessage());

        return chargingSessionDTO;
    }
}
