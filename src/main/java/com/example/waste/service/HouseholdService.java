package com.example.waste.service;

import com.example.waste.model.Household;
import com.example.waste.repository.CollectorRepository;
import com.example.waste.repository.HouseholdRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HouseholdService {

    private final HouseholdRepository householdRepository;
    private final CollectorRepository collectorRepository;

    public HouseholdService(HouseholdRepository householdRepository, CollectorRepository collectorRepository) {
        this.householdRepository = householdRepository;
        this.collectorRepository = collectorRepository;
    }

    public Household saveHousehold(Household household) {
        return householdRepository.save(household);
    }

    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    public Optional<Household> getHouseholdById(UUID id) {
        return householdRepository.findById(id);
    }

    public String updateHousehold(UUID id, Household householdDetails) {
        Optional<Household> existing = householdRepository.findById(id);
        if (existing.isEmpty()) {
            return "The household with that id does not exist";
        }
        Household household = existing.get();
        household.setHeadFirstName(householdDetails.getHeadFirstName());
        household.setHeadLastName(householdDetails.getHeadLastName());
        household.setHouseCode(householdDetails.getHouseCode());
        household.setVillage(householdDetails.getVillage());
        household.setPhone(householdDetails.getPhone());
        householdRepository.save(household);
        return "Household updated successfully";
    }

    public String deleteHousehold(UUID id) {
        if (!householdRepository.existsById(id)) {
            return "The household with that id does not exist";
        }
        householdRepository.deleteById(id);
        return "Household deleted successfully";
    }

    public List<Household> getHouseholdsByVillage(String village) {
        return householdRepository.findByVillageIgnoreCaseOrderByHeadLastNameAsc(village);
    }

    public Object getHouseholdsOfCollector(UUID collectorId) {
        if (!collectorRepository.existsById(collectorId)) {
            return "The collector with that id does not exist";
        }
        return householdRepository.findHouseholdsByCollectorId(collectorId);
    }
}