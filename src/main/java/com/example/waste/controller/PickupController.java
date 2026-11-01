package com.example.waste.controller;

import com.example.waste.model.Pickup;
import com.example.waste.model.PickupStatus;
import com.example.waste.service.PickupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pickups")
public class PickupController {

    private final PickupService pickupService;

    public PickupController(PickupService pickupService) {
        this.pickupService = pickupService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePickup(@RequestBody Pickup pickup) {
        String result = pickupService.savePickup(pickup);
        if (result.contains("cannot be in the past")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        if (result.contains("already has a pickup")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<String> createPickupDirect(@RequestBody Pickup pickup) {
        return savePickup(pickup);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pickup>> getAllPickups() {
        return ResponseEntity.ok(pickupService.getAllPickups());
    }

    @GetMapping
    public ResponseEntity<List<Pickup>> getAllPickupsDirect() {
        return getAllPickups();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPickupById(@PathVariable UUID id) {
        Optional<Pickup> pickup = pickupService.getPickupById(id);
        if (pickup.isEmpty()) {
            return new ResponseEntity<>("The pickup with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pickup.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePickupDirect(@PathVariable UUID id, @RequestBody Pickup pickup) {
        return updatePickup(id, pickup);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePickup(@PathVariable UUID id, @RequestBody Pickup pickup) {
        String result = pickupService.updatePickup(id, pickup);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (result.contains("already has a pickup")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePickupDirect(@PathVariable UUID id) {
        return deletePickup(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePickup(@PathVariable UUID id) {
        String result = pickupService.deletePickup(id);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (result.contains("has disposal records")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pickup>> getPickupsByStatusPath(@PathVariable PickupStatus status) {
        return ResponseEntity.ok(pickupService.getPickupsByStatus(status));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Pickup>> getPickupsByStatus(@RequestParam PickupStatus status) {
        return ResponseEntity.ok(pickupService.getPickupsByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<Pickup>> getPickupsBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(pickupService.getPickupsBetween(startDate, endDate));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam PickupStatus status) {
        String result = pickupService.updateStatus(id, status);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (result.contains("Cannot change status")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return ResponseEntity.ok(pickupService.getStatsByStatus());
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDayForCollector(@RequestParam UUID collectorId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        String result = pickupService.cancelDayForCollector(collectorId, localDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Pickup>> getPickupsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "pickupDate,desc") String sort) {

        String[] sortParts = sort.split(",");
        String property = sortParts[0];
        Sort.Direction direction = (sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, property));
        return ResponseEntity.ok(pickupService.getPickupsPaged(pageable));
    }
}