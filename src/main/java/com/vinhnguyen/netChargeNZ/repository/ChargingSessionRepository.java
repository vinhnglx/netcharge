package com.vinhnguyen.netChargeNZ.repository;

import com.vinhnguyen.netChargeNZ.model.ChargingSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingSessionRepository extends CrudRepository<ChargingSession, Integer> {
}
