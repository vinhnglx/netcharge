package com.vinhnguyen.netChargeNZ.repository;

import com.vinhnguyen.netChargeNZ.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
}
