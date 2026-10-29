package com.example.waste.config;

import com.example.waste.model.*;
import com.example.waste.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SeedData {

    @Bean
    CommandLineRunner initDatabase(
            WasteTypeRepository wasteTypeRepo,
            TruckRepository truckRepo,
            CollectorRepository collectorRepo,
            HouseholdRepository householdRepo,
            PickupRepository pickupRepo) {
        return args -> {
            if (wasteTypeRepo.count() == 0) {
                
              
                WasteType organic = new WasteType();
                organic.setName("Organic");
                organic = wasteTypeRepo.save(organic);

                WasteType plastic = new WasteType();
                plastic.setName("Plastic");
                plastic = wasteTypeRepo.save(plastic);

                WasteType paper = new WasteType();
                paper.setName("Paper");
                paper = wasteTypeRepo.save(paper);

                WasteType eWaste = new WasteType();
                eWaste.setName("E-Waste");
                eWaste = wasteTypeRepo.save(eWaste);

                
                Truck t1 = new Truck();
                t1.setTruckNumber(1);
                t1.setPlateNumber("RAC123A");
                t1 = truckRepo.save(t1);

                Truck t2 = new Truck();
                t2.setTruckNumber(2);
                t2.setPlateNumber("RAC456B");
                t2 = truckRepo.save(t2);

                Truck t3 = new Truck();
                t3.setTruckNumber(3);
                t3.setPlateNumber("RAC789C");
                t3 = truckRepo.save(t3);

                Truck t4 = new Truck();
                t4.setTruckNumber(4);
                t4.setPlateNumber("RAC000D");
                t4 = truckRepo.save(t4);

                
                Collector c1 = new Collector();
                c1.setFirstName("Eric");
                c1.setLastName("Rukundo");
                c1.setPhone("0788666666");
                c1.setTruck(t1);
                c1.setWasteTypes(new ArrayList<>(List.of(organic, plastic)));
                c1 = collectorRepo.save(c1);

                Collector c2 = new Collector();
                c2.setFirstName("Alice");
                c2.setLastName("Mugabo");
                c2.setPhone("0788777777");
                c2.setTruck(t2);
                c2.setWasteTypes(new ArrayList<>(List.of(paper)));
                c2 = collectorRepo.save(c2);

                Collector c3 = new Collector();
                c3.setFirstName("John");
                c3.setLastName("Bizimana");
                c3.setPhone("0788888888");
                c3.setTruck(t3);
                c3.setWasteTypes(new ArrayList<>(List.of(eWaste)));
                c3 = collectorRepo.save(c3);

               
                Household h1 = createHousehold("Jean", "Mugisha", "KCK-GTG-0001", "Gatenga", "0788111111");
                h1 = householdRepo.save(h1);

                Household h2 = createHousehold("Alice", "Uwase", "KCK-GTG-0045", "Gatenga", "0788222222");
                h2 = householdRepo.save(h2);

                Household h3 = createHousehold("Paul", "Kamanzi", "KCK-NYR-0010", "Nyarugenge", "0788333333");
                h3 = householdRepo.save(h3);

                Household h4 = createHousehold("Marie", "Keza", "KCK-KIC-0022", "Kicukiro", "0788444444");
                h4 = householdRepo.save(h4);

                Household h5 = createHousehold("Claude", "Gakwaya", "KCK-KIC-0099", "Kicukiro", "0788555555");
                h5 = householdRepo.save(h5);

               
                createPickup(pickupRepo, LocalDate.of(2026, 11, 28), PickupStatus.COLLECTED, 3, h1, c1);
                createPickup(pickupRepo, LocalDate.of(2026, 11, 8), PickupStatus.ASSIGNED, 3, h2, c1);
                createPickup(pickupRepo, LocalDate.of(2026, 11, 6), PickupStatus.REQUESTED, 4, h4, c1);
                createPickup(pickupRepo, LocalDate.of(2026, 11, 5), PickupStatus.REQUESTED, 1, h3, c2);
                createPickup(pickupRepo, LocalDate.of(2026, 11, 4), PickupStatus.REQUESTED, 2, h1, c2);
                createPickup(pickupRepo, LocalDate.of(2026, 11, 3), PickupStatus.REQUESTED, 2, h5, c3);
                createPickup(pickupRepo, LocalDate.of(2026, 11, 2), PickupStatus.REQUESTED, 5, h2, c3);
                createPickup(pickupRepo, LocalDate.of(2026, 11, 1), PickupStatus.REQUESTED, 1, h3, c1);
                createPickup(pickupRepo, LocalDate.of(2026, 10, 30), PickupStatus.REQUESTED, 3, h4, c2);
                createPickup(pickupRepo, LocalDate.of(2026, 10, 29), PickupStatus.REQUESTED, 2, h5, c1);
            }
        };
    }

    private Household createHousehold(String firstName, String lastName, String code, String village, String phone) {
        Household h = new Household();
        h.setHeadFirstName(firstName);
        h.setHeadLastName(lastName);
        h.setHouseCode(code);
        h.setVillage(village);
        h.setPhone(phone);
        return h;
    }

    private void createPickup(PickupRepository repo, LocalDate date, PickupStatus status, Integer bags, Household household, Collector collector) {
        Pickup p = new Pickup();
        p.setPickupDate(date);
        p.setStatus(status);
        p.setBags(bags);
        p.setHousehold(household);
        p.setCollector(collector);
        repo.save(p);
    }
}