package com.example.waste.service;

import com.example.waste.model.DisposalRecord;
import com.example.waste.model.Pickup;
import com.example.waste.model.PickupStatus;
import com.example.waste.repository.DisposalRecordRepository;
import com.example.waste.repository.PickupRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DisposalRecordService {

    private final DisposalRecordRepository disposalRecordRepository;
    private final PickupRepository pickupRepository;

    public DisposalRecordService(DisposalRecordRepository disposalRecordRepository, PickupRepository pickupRepository) {
        this.disposalRecordRepository = disposalRecordRepository;
        this.pickupRepository = pickupRepository;
    }

    public String saveDisposalRecord(UUID pickupId, DisposalRecord record) {
        Optional<Pickup> pickupOpt = pickupRepository.findById(pickupId);
        if (pickupOpt.isEmpty()) {
            return "The pickup with that id does not exist";
        }

        Pickup pickup = pickupOpt.get();
        if (pickup.getStatus() != PickupStatus.COLLECTED) {
            return "Disposal records are only for COLLECTED pickups";
        }

        record.setPickup(pickup);
        record.setDisposedDate(LocalDate.now());
        disposalRecordRepository.save(record);
        return "Disposal record saved successfully";
    }

    public List<DisposalRecord> getRecordsByHousehold(String houseCode) {
        return disposalRecordRepository.findByHouseholdHouseCode(houseCode);
    }

    public String deleteDisposalRecord(UUID id) {
        if (!disposalRecordRepository.existsById(id)) {
            return "The disposal record with that id does not exist";
        }
        disposalRecordRepository.deleteById(id);
        return "Disposal record deleted successfully";
    }
}