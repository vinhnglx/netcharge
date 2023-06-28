package com.vinhnguyen.netChargeNZ.service;

import com.vinhnguyen.netChargeNZ.controller.request.CreateConnectorRequest;
import com.vinhnguyen.netChargeNZ.model.ChargePoint;
import com.vinhnguyen.netChargeNZ.model.Connector;
import com.vinhnguyen.netChargeNZ.repository.ChargePointRepository;
import com.vinhnguyen.netChargeNZ.repository.ConnectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConnectorService {
    private ConnectorRepository connectorRepository;
    private ChargePointRepository chargePointRepository;

    public Connector createConnect(CreateConnectorRequest connectorRequest) {
        Connector newConnector = new Connector();
        newConnector.setConnectorNumber(connectorRequest.getConnector_number());
        Optional<ChargePoint> chargePointOptional = chargePointRepository.findById(connectorRequest.getCharge_point_id());

        if (chargePointOptional.isPresent()) {
            ChargePoint chargePoint = chargePointOptional.get();
            newConnector.setChargePoint(chargePoint);
            return connectorRepository.save(newConnector);
        }

        return null;
    }
}
