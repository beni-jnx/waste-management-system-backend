package com.example.waste.repository;

import com.example.waste.model.DisposalRecord;
import com.example.waste.model.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DisposalRecordRepository extends JpaRepository<DisposalRecord, UUID> {

    boolean existsByPickup(Pickup pickup);

    @Query("SELECT d FROM DisposalRecord d WHERE d.pickup.household.houseCode = :houseCode ORDER BY d.disposedDate DESC")
    List<DisposalRecord> findByHouseholdHouseCode(@Param("houseCode") String houseCode);
}