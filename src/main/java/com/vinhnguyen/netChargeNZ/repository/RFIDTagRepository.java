package com.vinhnguyen.netChargeNZ.repository;

import com.vinhnguyen.netChargeNZ.model.RFIDTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFIDTagRepository extends CrudRepository<RFIDTag, Integer> {
}

