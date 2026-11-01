package com.example.waste.controller;

import com.example.waste.model.WasteType;
import com.example.waste.service.WasteTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/waste-types")
public class WasteTypeController {

    private final WasteTypeService wasteTypeService;

    public WasteTypeController(WasteTypeService wasteTypeService) {
        this.wasteTypeService = wasteTypeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveWasteType(@RequestBody WasteType wasteType) {
        wasteTypeService.saveWasteType(wasteType);
        return new ResponseEntity<>("Waste type saved successfully", HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<String> createWasteTypeDirect(@RequestBody WasteType wasteType) {
        return saveWasteType(wasteType);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WasteType>> getAllWasteTypes() {
        return ResponseEntity.ok(wasteTypeService.getAllWasteTypes());
    }

    @GetMapping
    public ResponseEntity<List<WasteType>> getAllWasteTypesDirect() {
        return getAllWasteTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWasteTypeById(@PathVariable UUID id) {
        Optional<WasteType> wasteType = wasteTypeService.getWasteTypeById(id);
        if (wasteType.isEmpty()) {
            return new ResponseEntity<>("The waste type with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(wasteType.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateWasteTypeDirect(@PathVariable UUID id, @RequestBody WasteType wasteType) {
        return updateWasteType(id, wasteType);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWasteType(@PathVariable UUID id, @RequestBody WasteType wasteType) {
        String result = wasteTypeService.updateWasteType(id, wasteType);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWasteTypeDirect(@PathVariable UUID id) {
        return deleteWasteType(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWasteType(@PathVariable UUID id) {
        String result = wasteTypeService.deleteWasteType(id);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<WasteType>> getUnusedWasteTypes() {
        return ResponseEntity.ok(wasteTypeService.getUnusedWasteTypes());
    }
}