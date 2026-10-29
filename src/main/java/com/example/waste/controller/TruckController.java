package com.example.waste.controller;

import com.example.waste.model.Truck;
import com.example.waste.service.TruckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {

    private final TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTruck(@RequestBody Truck truck) {
        truckService.saveTruck(truck);
        return new ResponseEntity<>("Truck saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Truck>> getAllTrucks() {
        return ResponseEntity.ok(truckService.getAllTrucks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTruckById(@PathVariable UUID id) {
        Optional<Truck> truck = truckService.getTruckById(id);
        if (truck.isEmpty()) {
            return new ResponseEntity<>("The truck with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(truck.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTruck(@PathVariable UUID id, @RequestBody Truck truck) {
        String result = truckService.updateTruck(id, truck);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTruck(@PathVariable UUID id) {
        String result = truckService.deleteTruck(id);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/free")
    public ResponseEntity<List<Truck>> getFreeTrucks() {
        return ResponseEntity.ok(truckService.getFreeTrucks());
    }
}