package com.vinhnguyen.netChargeNZ.repository;

import com.vinhnguyen.netChargeNZ.model.ChargingSession;
import com.vinhnguyen.netChargeNZ.model.Connector;
import com.vinhnguyen.netChargeNZ.model.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingSessionRepository extends CrudRepository<ChargingSession, Integer> {
    @Query("SELECT CASE WHEN COUNT(cs) > 0 THEN TRUE ELSE FALSE END " +
            "FROM ChargingSession cs " +
            "WHERE cs.connector.id = :connectorId " +
            "AND cs.vehicle.id = :vehicleId " +
            "AND cs.endTime IS NULL")
    boolean existsByConnectorAndVehicleAndEndTimeIsNull(
            @Param("connectorId") int connectorId,
            @Param("vehicleId") int vehicleId);
}
