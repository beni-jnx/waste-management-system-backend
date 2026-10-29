package com.example.waste.service;

import com.example.waste.model.WasteType;
import com.example.waste.repository.WasteTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WasteTypeService {

    private final WasteTypeRepository wasteTypeRepository;

    public WasteTypeService(WasteTypeRepository wasteTypeRepository) {
        this.wasteTypeRepository = wasteTypeRepository;
    }

    public WasteType saveWasteType(WasteType wasteType) {
        return wasteTypeRepository.save(wasteType);
    }

    public List<WasteType> getAllWasteTypes() {
        return wasteTypeRepository.findAll();
    }

    public Optional<WasteType> getWasteTypeById(UUID id) {
        return wasteTypeRepository.findById(id);
    }

    public String updateWasteType(UUID id, WasteType wasteTypeDetails) {
        Optional<WasteType> existing = wasteTypeRepository.findById(id);
        if (existing.isEmpty()) {
            return "The waste type with that id does not exist";
        }
        WasteType wasteType = existing.get();
        wasteType.setName(wasteTypeDetails.getName());
        wasteTypeRepository.save(wasteType);
        return "Waste type updated successfully";
    }

    public String deleteWasteType(UUID id) {
        if (!wasteTypeRepository.existsById(id)) {
            return "The waste type with that id does not exist";
        }
        wasteTypeRepository.deleteById(id);
        return "Waste type deleted successfully";
    }

    public List<WasteType> getUnusedWasteTypes() {
        return wasteTypeRepository.findUnusedWasteTypes();
    }
}