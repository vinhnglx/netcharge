package com.vinhnguyen.netChargeNZ.repository;

import com.vinhnguyen.netChargeNZ.model.ChargePoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargePointRepository extends CrudRepository<ChargePoint, Integer> {
    List<ChargePoint> findByOwnerId(int ownerId);
}

