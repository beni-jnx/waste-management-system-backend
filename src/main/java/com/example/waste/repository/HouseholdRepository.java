package com.example.waste.repository;

import com.example.waste.model.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseholdRepository extends JpaRepository<Household, UUID> {

    Optional<Household> findByHouseCode(String houseCode);

    List<Household> findByVillageIgnoreCaseOrderByHeadLastNameAsc(String village);

    @Query("SELECT DISTINCT p.household FROM Pickup p WHERE p.collector.id = :collectorId")
    List<Household> findHouseholdsByCollectorId(@Param("collectorId") UUID collectorId);
}