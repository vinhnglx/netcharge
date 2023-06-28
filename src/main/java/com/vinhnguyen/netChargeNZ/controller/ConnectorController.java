package com.vinhnguyen.netChargeNZ.controller;

import com.vinhnguyen.netChargeNZ.controller.request.CreateConnectorRequest;
import com.vinhnguyen.netChargeNZ.controller.response.ChargePointDTO;
import com.vinhnguyen.netChargeNZ.controller.response.ConnectorDTO;
import com.vinhnguyen.netChargeNZ.model.ChargePoint;
import com.vinhnguyen.netChargeNZ.model.Connector;
import com.vinhnguyen.netChargeNZ.service.ConnectorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ConnectorController {
    private final ConnectorService connectorService;

    @PostMapping("/connectors")
    public ResponseEntity<ConnectorDTO> addNewConnector(@RequestBody CreateConnectorRequest requestConnector) {
        Connector connector = connectorService.createConnect(requestConnector);
        if (connector == null) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ConnectorDTO connectorDTO = new ConnectorDTO();
        connectorDTO.setConnectorNumber(connector.getConnectorNumber());
        connectorDTO.setId(connector.getId());

        ChargePointDTO chargePointDTO = new ChargePointDTO();
        chargePointDTO.setId(connector.getChargePoint().getId());
        chargePointDTO.setName(connector.getChargePoint().getName());
        chargePointDTO.setSerialNumber(connector.getChargePoint().getSerialNumber());
        connectorDTO.setChargePoint(chargePointDTO);

        return ResponseEntity.status(HttpStatus.OK).body(connectorDTO);
    }
}
