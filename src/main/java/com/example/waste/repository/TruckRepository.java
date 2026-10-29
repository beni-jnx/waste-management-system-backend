package com.example.waste.repository;

import com.example.waste.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TruckRepository extends JpaRepository<Truck, UUID> {

    Optional<Truck> findByTruckNumber(int truckNumber);

    @Query("SELECT t FROM Truck t WHERE NOT EXISTS (SELECT c FROM Collector c WHERE c.truck = t) ORDER BY t.truckNumber ASC")
    List<Truck> findFreeTrucks();
}