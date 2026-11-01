package com.example.waste.controller;

import com.example.waste.model.Household;
import com.example.waste.service.HouseholdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

   
    @PostMapping("/save")
    public ResponseEntity<String> saveHousehold(@RequestBody Household household) {
        householdService.saveHousehold(household);
        return new ResponseEntity<>("Household saved successfully", HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<String> createHouseholdDirect(@RequestBody Household household) {
        return saveHousehold(household);
    }

    
    @GetMapping("/all")
    public ResponseEntity<List<Household>> getAllHouseholds() {
        return ResponseEntity.ok(householdService.getAllHouseholds());
    }

    @GetMapping
    public ResponseEntity<List<Household>> getAllHouseholdsDirect() {
        return getAllHouseholds();
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<?> getHouseholdById(@PathVariable UUID id) {
        Optional<Household> household = householdService.getHouseholdById(id);
        if (household.isEmpty()) {
            return new ResponseEntity<>("The household with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(household.get());
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateHouseholdDirect(@PathVariable UUID id, @RequestBody Household household) {
        return updateHousehold(id, household);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateHousehold(@PathVariable UUID id, @RequestBody Household household) {
        String result = householdService.updateHousehold(id, household);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHouseholdDirect(@PathVariable UUID id) {
        return deleteHousehold(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHousehold(@PathVariable UUID id) {
        String result = householdService.deleteHousehold(id);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    
    @GetMapping("/by-village")
    public ResponseEntity<List<Household>> getHouseholdsByVillage(@RequestParam String village) {
        return ResponseEntity.ok(householdService.getHouseholdsByVillage(village));
    }

    @GetMapping("/of-collector/{collectorId}")
    public ResponseEntity<?> getHouseholdsOfCollector(@PathVariable UUID collectorId) {
        Object result = householdService.getHouseholdsOfCollector(collectorId);
        if (result instanceof String) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
}