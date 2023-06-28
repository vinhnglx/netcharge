package com.vinhnguyen.netChargeNZ.service;


import com.vinhnguyen.netChargeNZ.model.ChargePoint;
import com.vinhnguyen.netChargeNZ.repository.ChargePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChargePointService {
    private final ChargePointRepository chargePointRepository;

    public List<ChargePoint> getChargePointsByOwnerId(int ownerId) {
        return chargePointRepository.findByOwnerId(ownerId);
    }
}
