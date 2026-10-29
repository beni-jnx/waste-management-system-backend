package com.example.waste.config;

import com.example.waste.model.*;
import com.example.waste.repository.*;
import com.example.waste.service.*;
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
            WasteTypeService wasteTypeService,
            TruckService truckService,
            CollectorService collectorService,
            HouseholdService householdService,
            PickupService pickupService,
            WasteTypeRepository wasteTypeRepo) {
        return args -> {
            if (wasteTypeRepo.count() == 0) {

                
                WasteType organic = new WasteType();
                organic.setName("Organic");
                organic = wasteTypeService.saveWasteType(organic);

                WasteType plastic = new WasteType();
                plastic.setName("Plastic");
                plastic = wasteTypeService.saveWasteType(plastic);

                WasteType glass = new WasteType();
                glass.setName("Glass");
                glass = wasteTypeService.saveWasteType(glass);

                WasteType electronic = new WasteType();
                electronic.setName("Electronic");
                wasteTypeService.saveWasteType(electronic);

                
                Truck t1 = new Truck();
                t1.setTruckNumber(1);
                t1.setPlateNumber("RAC123A");
                t1 = truckService.saveTruck(t1);

                Truck t2 = new Truck();
                t2.setTruckNumber(2);
                t2.setPlateNumber("RAC456B");
                t2 = truckService.saveTruck(t2);

                Truck t3 = new Truck();
                t3.setTruckNumber(3);
                t3.setPlateNumber("RAC789C");
                t3 = truckService.saveTruck(t3);

                Truck t4 = new Truck();
                t4.setTruckNumber(4);
                t4.setPlateNumber("RAC000D");
                t4 = truckService.saveTruck(t4);

                
                Collector c1 = new Collector();
                c1.setFirstName("Eric");
                c1.setLastName("Rukundo");
                c1.setPhone("0788666666");
                c1.setTruck(t1);
                c1.setWasteTypes(new ArrayList<>(List.of(organic, plastic)));
                c1 = collectorService.saveCollector(c1);

                Collector c2 = new Collector();
                c2.setFirstName("Alice");
                c2.setLastName("Mugabo");
                c2.setPhone("0788777777");
                c2.setTruck(t2);
                c2.setWasteTypes(new ArrayList<>(List.of(glass)));
                c2 = collectorService.saveCollector(c2);

                Collector c3 = new Collector();
                c3.setFirstName("John");
                c3.setLastName("Bizimana");
                c3.setPhone("0788888888");
                c3.setTruck(t3);
                c3 = collectorService.saveCollector(c3);

                
                Household h1 = householdService.saveHousehold(createHousehold("Jean", "Mugisha", "KCK-GTG-0001", "Gatenga", "0788111111"));
                Household h2 = householdService.saveHousehold(createHousehold("Alice", "Uwase", "KCK-GTG-0045", "Gatenga", "0788222222"));
                Household h3 = householdService.saveHousehold(createHousehold("Paul", "Kamanzi", "KCK-NYR-0010", "Nyarugenge", "0788333333"));
                Household h4 = householdService.saveHousehold(createHousehold("Marie", "Keza", "KCK-KIC-0022", "Kicukiro", "0788444444"));
                Household h5 = householdService.saveHousehold(createHousehold("Claude", "Gakwaya", "KCK-KIC-0099", "Kicukiro", "0788555555"));

                
                Pickup p1 = createPickup(LocalDate.of(2026, 10, 20), 3, h1, c1);
                pickupService.savePickup(p1);
                pickupService.updateStatus(p1.getId(), PickupStatus.ASSIGNED);
                pickupService.updateStatus(p1.getId(), PickupStatus.COLLECTED);

                
                Pickup p2 = createPickup(LocalDate.of(2026, 10, 20), 2, h2, c1);
                pickupService.savePickup(p2);
                pickupService.updateStatus(p2.getId(), PickupStatus.ASSIGNED);
                pickupService.updateStatus(p2.getId(), PickupStatus.COLLECTED);

                
                Pickup p3 = createPickup(LocalDate.of(2026, 10, 25), 4, h1, c2);
                pickupService.savePickup(p3);
                pickupService.updateStatus(p3.getId(), PickupStatus.SKIPPED);

                
                Pickup p4 = createPickup(LocalDate.of(2026, 11, 5), 1, h1, c3);
                pickupService.savePickup(p4);

               
                Pickup p5 = createPickup(LocalDate.of(2026, 11, 4), 2, h3, c2);
                pickupService.savePickup(p5);

                Pickup p6 = createPickup(LocalDate.of(2026, 11, 3), 2, h5, c3);
                pickupService.savePickup(p6);

                Pickup p7 = createPickup(LocalDate.of(2026, 11, 2), 5, h2, c3);
                pickupService.savePickup(p7);

                Pickup p8 = createPickup(LocalDate.of(2026, 11, 1), 1, h3, c1);
                pickupService.savePickup(p8);

                Pickup p9 = createPickup(LocalDate.of(2026, 10, 30), 3, h4, c2);
                pickupService.savePickup(p9);

                Pickup p10 = createPickup(LocalDate.of(2026, 10, 29), 2, h5, c1);
                pickupService.savePickup(p10);
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

    private Pickup createPickup(LocalDate date, Integer bags, Household household, Collector collector) {
        Pickup p = new Pickup();
        p.setPickupDate(date);
        p.setBags(bags);
        p.setHousehold(household);
        p.setCollector(collector);
        return p;
    }
}