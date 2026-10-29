package com.example.waste.controller;

import com.example.waste.model.DisposalRecord;
import com.example.waste.service.DisposalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/disposal-records")
public class DisposalRecordController {

    private final DisposalRecordService disposalRecordService;

    public DisposalRecordController(DisposalRecordService disposalRecordService) {
        this.disposalRecordService = disposalRecordService;
    }

    @RequestMapping(value = {"", "/save"}, method = RequestMethod.POST)
    public ResponseEntity<String> saveDisposalRecord(
            @RequestParam(required = false) UUID pickupId,
            @RequestBody DisposalRecord record) {

        UUID targetPickupId = pickupId;
        if (targetPickupId == null && record.getPickup() != null) {
            targetPickupId = record.getPickup().getId();
        }

        if (targetPickupId == null) {
            return new ResponseEntity<>("Pickup ID is required", HttpStatus.BAD_REQUEST);
        }

        String result = disposalRecordService.saveDisposalRecord(targetPickupId, record);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (result.contains("only for COLLECTED") || result.contains("cannot be")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/household/{houseCode}")
    public ResponseEntity<List<DisposalRecord>> getRecordsByHousehold(@PathVariable String houseCode) {
        return ResponseEntity.ok(disposalRecordService.getRecordsByHousehold(houseCode));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDisposalRecord(@PathVariable UUID id) {
        String result = disposalRecordService.deleteDisposalRecord(id);
        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
}