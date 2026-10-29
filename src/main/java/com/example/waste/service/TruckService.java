package com.example.waste.service;

import com.example.waste.model.Truck;
import com.example.waste.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TruckService {

    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public Truck saveTruck(Truck truck) {
        return truckRepository.save(truck);
    }

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public Optional<Truck> getTruckById(UUID id) {
        return truckRepository.findById(id);
    }

    public String updateTruck(UUID id, Truck truckDetails) {
        Optional<Truck> existing = truckRepository.findById(id);
        if (existing.isEmpty()) {
            return "The truck with that id does not exist";
        }
        Truck truck = existing.get();
        truck.setTruckNumber(truckDetails.getTruckNumber());
        truck.setPlateNumber(truckDetails.getPlateNumber());
        truckRepository.save(truck);
        return "Truck updated successfully";
    }

    public String deleteTruck(UUID id) {
        if (!truckRepository.existsById(id)) {
            return "The truck with that id does not exist";
        }
        truckRepository.deleteById(id);
        return "Truck deleted successfully";
    }

    public List<Truck> getFreeTrucks() {
        return truckRepository.findFreeTrucks();
    }
}