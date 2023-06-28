package com.vinhnguyen.netChargeNZ.controller;

import com.vinhnguyen.netChargeNZ.constants.SecurityConstants;
import com.vinhnguyen.netChargeNZ.controller.response.ChargePointDTO;
import com.vinhnguyen.netChargeNZ.controller.response.ConnectorDTOLight;
import com.vinhnguyen.netChargeNZ.controller.response.UserDTO;
import com.vinhnguyen.netChargeNZ.model.ChargePoint;
import com.vinhnguyen.netChargeNZ.model.Connector;
import com.vinhnguyen.netChargeNZ.model.User;
import com.vinhnguyen.netChargeNZ.service.ChargePointService;
import com.vinhnguyen.netChargeNZ.service.UserService;
import com.vinhnguyen.netChargeNZ.util.JWTTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ChargePointController {

    private final JWTTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ChargePointService chargePointService;

    @GetMapping("/chargePoints")
    public ResponseEntity<?> getAllChargePointsBelongToUser(HttpServletRequest request) {
        String jwtHeader = request.getHeader(SecurityConstants.JWT_HEADER);
        if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
            String jwt = jwtHeader.replace("Bearer ", "").trim();
            String username = getUsername(jwt);

            User user = userService.findByUsername(username);
            List<ChargePoint> chargePoints = chargePointService.getChargePointsByOwnerId(user.getId());

            List<ChargePointDTO> chargePointDTOs = chargePoints.stream()
                    .map(this::buildChargePointDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(chargePointDTOs);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    private ChargePointDTO buildChargePointDTO(ChargePoint chargePoint) {
        ChargePointDTO chargePointDTO = new ChargePointDTO();
        chargePointDTO.setId(chargePoint.getId());
        chargePointDTO.setName(chargePoint.getName());
        chargePointDTO.setSerialNumber(chargePoint.getSerialNumber());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(chargePoint.getOwner().getId());
        userDTO.setRole(chargePoint.getOwner().getRole());
        userDTO.setUsername(chargePoint.getOwner().getUsername());
        chargePointDTO.setOwner(userDTO);

        List<Connector> connectors = chargePoint.getConnectors();
        List<ConnectorDTOLight> connectorDTOs = connectors.stream()
                .map(this::buildConnectorDTO)
                .collect(Collectors.toList());
        chargePointDTO.setConnectors(connectorDTOs);
        return chargePointDTO;
    }

    private ConnectorDTOLight buildConnectorDTO(Connector connector) {
        ConnectorDTOLight connectorDTO = new ConnectorDTOLight();
        connectorDTO.setConnectorNumber(connector.getConnectorNumber());
        connectorDTO.setId(connector.getId());
        return connectorDTO;
    }

    private String getUsername(String jwt) {
        Claims claims = jwtTokenUtil.getPayload(jwt);
        return String.valueOf(claims.get("username"));
    }
}
