package com.example.waste.repository;

import com.example.waste.model.WasteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WasteTypeRepository extends JpaRepository<WasteType, UUID> {

    @Query("SELECT w FROM WasteType w WHERE w.collectors IS EMPTY")
    List<WasteType> findUnusedWasteTypes();
}