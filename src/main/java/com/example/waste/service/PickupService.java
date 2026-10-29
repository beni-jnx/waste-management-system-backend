package com.example.waste.service;

import com.example.waste.model.Pickup;
import com.example.waste.model.PickupStatus;
import com.example.waste.repository.DisposalRecordRepository;
import com.example.waste.repository.PickupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PickupService {

    private final PickupRepository pickupRepository;
    private final DisposalRecordRepository disposalRecordRepository;

    public PickupService(PickupRepository pickupRepository, DisposalRecordRepository disposalRecordRepository) {
        this.pickupRepository = pickupRepository;
        this.disposalRecordRepository = disposalRecordRepository;
    }

    public String savePickup(Pickup pickup) {
        if (pickup.getPickupDate().isBefore(LocalDate.now())) {
            return "Pickup date cannot be in the past";
        }
        if (pickup.getCollector() != null && pickupRepository.existsByCollectorAndPickupDateAndStatusNot(pickup.getCollector(), pickup.getPickupDate(), PickupStatus.SKIPPED)) {
            return "Collector already has a pickup on that date";
        }
        pickup.setStatus(PickupStatus.REQUESTED);
        pickupRepository.save(pickup);
        return "Pickup saved successfully";
    }

    public List<Pickup> getAllPickups() {
        return pickupRepository.findAll();
    }

    public Optional<Pickup> getPickupById(UUID id) {
        return pickupRepository.findById(id);
    }

    public String updatePickup(UUID id, Pickup pickupDetails) {
        Optional<Pickup> existing = pickupRepository.findById(id);
        if (existing.isEmpty()) {
            return "The pickup with that id does not exist";
        }
        Pickup pickup = existing.get();
        pickup.setPickupDate(pickupDetails.getPickupDate());
        pickup.setBags(pickupDetails.getBags());
        pickup.setHousehold(pickupDetails.getHousehold());
        pickup.setCollector(pickupDetails.getCollector());
        pickupRepository.save(pickup);
        return "Pickup updated successfully";
    }

    public String deletePickup(UUID id) {
        Optional<Pickup> existing = pickupRepository.findById(id);
        if (existing.isEmpty()) {
            return "The pickup with that id does not exist";
        }
        Pickup pickup = existing.get();
        if (disposalRecordRepository.existsByPickup(pickup)) {
            return "That pickup has disposal records, delete them first";
        }
        pickupRepository.delete(pickup);
        return "Pickup deleted successfully";
    }

    public List<Pickup> getPickupsByStatus(PickupStatus status) {
        return pickupRepository.findByStatusOrderByPickupDateAsc(status);
    }

    public List<Pickup> getPickupsBetween(LocalDate start, LocalDate end) {
        return pickupRepository.findByPickupDateBetweenOrderByPickupDateAsc(start, end);
    }

    public String updateStatus(UUID id, PickupStatus newStatus) {
        Optional<Pickup> existing = pickupRepository.findById(id);
        if (existing.isEmpty()) {
            return "The pickup with that id does not exist";
        }

        Pickup pickup = existing.get();
        PickupStatus currentStatus = pickup.getStatus();

        boolean allowed = false;
        if (currentStatus == PickupStatus.REQUESTED && (newStatus == PickupStatus.ASSIGNED || newStatus == PickupStatus.SKIPPED)) {
            allowed = true;
        } else if (currentStatus == PickupStatus.ASSIGNED && (newStatus == PickupStatus.COLLECTED || newStatus == PickupStatus.SKIPPED)) {
            allowed = true;
        }

        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        pickup.setStatus(newStatus);
        pickupRepository.save(pickup);
        return "Pickup status updated successfully";
    }

    public List<Object[]> getStatsByStatus() {
        return pickupRepository.countPickupsByStatus();
    }

    @org.springframework.transaction.annotation.Transactional
    public String cancelDayForCollector(UUID collectorId, LocalDate date) {
        int count = pickupRepository.cancelDayForCollector(collectorId, date);
        return count + " pickups cancelled";
    }

    public Page<Pickup> getPickupsPaged(Pageable pageable) {
        return pickupRepository.findAll(pageable);
    }
}