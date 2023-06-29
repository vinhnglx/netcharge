package com.vinhnguyen.netChargeNZ.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ChargingSessionController {

    private final ChargingSessionService chargingSessionService;

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
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }
}
