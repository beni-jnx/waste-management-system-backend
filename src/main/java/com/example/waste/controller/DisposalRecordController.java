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

    @PostMapping("/save")
    public ResponseEntity<String> saveDisposalRecord(
            @RequestParam UUID pickupId,
            @RequestBody DisposalRecord record) {

        String result = disposalRecordService.saveDisposalRecord(pickupId, record);

        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (result.contains("only for COLLECTED") || result.contains("cannot be") || result.contains("must be")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<String> saveDisposalRecordDirect(
            @RequestParam UUID pickupId,
            @RequestBody DisposalRecord record) {
        return saveDisposalRecord(pickupId, record);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDisposalRecordDirect(@PathVariable UUID id) {
        return deleteDisposalRecord(id);
    }
}