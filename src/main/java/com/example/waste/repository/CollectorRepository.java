package com.example.waste.repository;

import com.example.waste.model.Collector;
import com.example.waste.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CollectorRepository extends JpaRepository<Collector, UUID> {

    boolean existsByTruck(Truck truck);

    @Query("SELECT DISTINCT c FROM Collector c JOIN c.wasteTypes w WHERE LOWER(w.name) = LOWER(:name)")
    List<Collector> findByWasteTypeNameIgnoreCase(@Param("name") String name);
}