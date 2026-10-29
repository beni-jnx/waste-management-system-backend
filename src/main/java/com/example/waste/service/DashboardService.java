package com.example.waste.service;

import com.example.waste.model.PickupStatus;
import com.example.waste.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final HouseholdRepository householdRepository;
    private final CollectorRepository collectorRepository;
    private final TruckRepository truckRepository;
    private final PickupRepository pickupRepository;
    private final DisposalRecordRepository disposalRecordRepository;

    public DashboardService(HouseholdRepository householdRepository, CollectorRepository collectorRepository, TruckRepository truckRepository, PickupRepository pickupRepository, DisposalRecordRepository disposalRecordRepository) {
        this.householdRepository = householdRepository;
        this.collectorRepository = collectorRepository;
        this.truckRepository = truckRepository;
        this.pickupRepository = pickupRepository;
        this.disposalRecordRepository = disposalRecordRepository;
    }

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("households", householdRepository.count());
        summary.put("collectors", collectorRepository.count());
        summary.put("freeTrucks", (long) truckRepository.findFreeTrucks().size());
        summary.put("pickupsToday", pickupRepository.countByPickupDate(LocalDate.now()));
        summary.put("requested", pickupRepository.countByStatus(PickupStatus.REQUESTED));
        summary.put("disposalRecords", disposalRecordRepository.count());
        return summary;
    }
}