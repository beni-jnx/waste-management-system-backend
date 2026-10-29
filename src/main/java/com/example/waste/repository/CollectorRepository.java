package com.example.waste.repository;

import com.example.waste.model.Collector;
import com.example.waste.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollectorRepository extends JpaRepository<Collector, UUID> {
    
    Optional<Collector> findByTruck(Truck truck);

    @Query("SELECT DISTINCT c FROM Collector c JOIN c.wasteTypes w WHERE LOWER(w.name) = LOWER(:name)")
    List<Collector> findByWasteTypeNameIgnoreCase(@Param("name") String name);
}