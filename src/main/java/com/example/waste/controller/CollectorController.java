package com.example.waste.controller;

import com.example.waste.model.Collector;
import com.example.waste.service.CollectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/collectors")
public class CollectorController {

    private final CollectorService collectorService;

    public CollectorController(CollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCollector(@RequestBody Collector collector) {
        collectorService.saveCollector(collector);
        return new ResponseEntity<>("Collector saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Collector>> getAllCollectors() {
        return ResponseEntity.ok(collectorService.getAllCollectors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectorById(@PathVariable UUID id) {
        Optional<Collector> collector = collectorService.getCollectorById(id);
        if (collector.isEmpty()) {
            return new ResponseEntity<>("The collector with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(collector.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCollector(@PathVariable UUID id, @RequestBody Collector collector) {
        String result = collectorService.updateCollector(id, collector);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCollector(@PathVariable UUID id) {
        String result = collectorService.deleteCollector(id);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-waste-type")
    public ResponseEntity<List<Collector>> getCollectorsByWasteType(@RequestParam String name) {
        return ResponseEntity.ok(collectorService.getCollectorsByWasteType(name));
    }

    @RequestMapping(value = "/{collectorId}/waste-types/{wasteTypeId}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<String> addWasteTypeToCollector(@PathVariable UUID collectorId, @PathVariable UUID wasteTypeId) {
        String result = collectorService.addWasteTypeToCollector(collectorId, wasteTypeId);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (result.contains("already has")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/{collectorId}/truck", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<String> changeTruck(@PathVariable UUID collectorId, @RequestParam int truckNumber) {
        String result = collectorService.changeTruck(collectorId, truckNumber);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (result.contains("taken by another")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(result);
    }
}