package com.example.waste.service;

import com.example.waste.model.Collector;
import com.example.waste.model.Truck;
import com.example.waste.model.WasteType;
import com.example.waste.repository.CollectorRepository;
import com.example.waste.repository.TruckRepository;
import com.example.waste.repository.WasteTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CollectorService {

    private final CollectorRepository collectorRepository;
    private final WasteTypeRepository wasteTypeRepository;
    private final TruckRepository truckRepository;

    public CollectorService(CollectorRepository collectorRepository, WasteTypeRepository wasteTypeRepository, TruckRepository truckRepository) {
        this.collectorRepository = collectorRepository;
        this.wasteTypeRepository = wasteTypeRepository;
        this.truckRepository = truckRepository;
    }

    public Collector saveCollector(Collector collector) {
        return collectorRepository.save(collector);
    }

    public List<Collector> getAllCollectors() {
        return collectorRepository.findAll();
    }

    public Optional<Collector> getCollectorById(UUID id) {
        return collectorRepository.findById(id);
    }

    public String updateCollector(UUID id, Collector collectorDetails) {
        Optional<Collector> existing = collectorRepository.findById(id);
        if (existing.isEmpty()) {
            return "The collector with that id does not exist";
        }
        Collector collector = existing.get();
        collector.setFirstName(collectorDetails.getFirstName());
        collector.setLastName(collectorDetails.getLastName());
        collector.setPhone(collectorDetails.getPhone());
        collector.setTruck(collectorDetails.getTruck());
        collector.setWasteTypes(collectorDetails.getWasteTypes());
        collectorRepository.save(collector);
        return "Collector updated successfully";
    }

    public String deleteCollector(UUID id) {
        if (!collectorRepository.existsById(id)) {
            return "The collector with that id does not exist";
        }
        collectorRepository.deleteById(id);
        return "Collector deleted successfully";
    }

    public List<Collector> getCollectorsByWasteType(String name) {
        return collectorRepository.findByWasteTypeNameIgnoreCase(name);
    }

    public String addWasteTypeToCollector(UUID collectorId, UUID wasteTypeId) {
        Optional<Collector> collectorOpt = collectorRepository.findById(collectorId);
        if (collectorOpt.isEmpty()) {
            return "The collector with that id does not exist";
        }
        Optional<WasteType> wasteTypeOpt = wasteTypeRepository.findById(wasteTypeId);
        if (wasteTypeOpt.isEmpty()) {
            return "That waste type does not exist";
        }

        Collector collector = collectorOpt.get();
        WasteType wasteType = wasteTypeOpt.get();

        if (collector.getWasteTypes() == null) {
            collector.setWasteTypes(new ArrayList<>());
        }

        if (collector.getWasteTypes().contains(wasteType)) {
            return "The collector already has that waste type";
        }

        collector.getWasteTypes().add(wasteType);
        collectorRepository.save(collector);
        return "Waste type added to the collector";
    }

    public String changeTruck(UUID collectorId, int truckNumber) {
        Optional<Collector> collectorOpt = collectorRepository.findById(collectorId);
        if (collectorOpt.isEmpty()) {
            return "The collector with that id does not exist";
        }

        Optional<Truck> truckOpt = truckRepository.findByTruckNumber(truckNumber);
        if (truckOpt.isEmpty()) {
            return "The truck with that truckNumber does not exist";
        }

        Collector collector = collectorOpt.get();
        Truck newTruck = truckOpt.get();

        Optional<Collector> currentOwnerOpt = collectorRepository.findByTruck(newTruck);
        if (currentOwnerOpt.isPresent()) {
            Collector currentOwner = currentOwnerOpt.get();
            if (!currentOwner.getId().equals(collector.getId())) {
                return "That truck is taken by another collector";
            }
        }

        collector.setTruck(newTruck);
        collectorRepository.save(collector);
        return "Truck changed successfully";
    }
}