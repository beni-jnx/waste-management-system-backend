package com.example.waste.repository;

import com.example.waste.model.Collector;
import com.example.waste.model.Pickup;
import com.example.waste.model.PickupStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PickupRepository extends JpaRepository<Pickup, UUID> {

    List<Pickup> findByStatusOrderByPickupDateAsc(PickupStatus status);

    List<Pickup> findByPickupDateBetweenOrderByPickupDateAsc(LocalDate start, LocalDate end);

    boolean existsByCollectorAndPickupDateAndStatusNot(Collector collector, LocalDate pickupDate, PickupStatus status);

    boolean existsByCollectorAndPickupDateAndIdNot(Collector collector, LocalDate pickupDate, UUID id);

    long countByStatus(PickupStatus status);

    long countByPickupDate(LocalDate date);

    @Query("SELECT p.status, COUNT(p) FROM Pickup p GROUP BY p.status")
    List<Object[]> countPickupsByStatus();

    @Modifying
    @Query("UPDATE Pickup p SET p.status = com.example.waste.model.PickupStatus.SKIPPED WHERE p.collector.id = :collectorId AND p.pickupDate = :date AND p.status <> com.example.waste.model.PickupStatus.COLLECTED")
    int cancelDayForCollector(@Param("collectorId") UUID collectorId, @Param("date") LocalDate date);
}