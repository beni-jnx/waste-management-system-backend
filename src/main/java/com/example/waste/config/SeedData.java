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
            WasteTypeRepository wasteTypeRepository,
            TruckRepository truckRepository,
            HouseholdRepository householdRepository,
            CollectorRepository collectorRepository,
            PickupRepository pickupRepository,
            DisposalRecordRepository disposalRecordRepository) {
        return args -> {
            if (wasteTypeRepository.count() > 0) {
                return;
            }

            WasteType organic = new WasteType();
            organic.setName("Organic");
            organic = wasteTypeRepository.save(organic);

            WasteType plastic = new WasteType();
            plastic.setName("Plastic");
            plastic = wasteTypeRepository.save(plastic);

            WasteType glass = new WasteType();
            glass.setName("Glass");
            glass = wasteTypeRepository.save(glass);

            WasteType electronic = new WasteType();
            electronic.setName("Electronic");
            wasteTypeRepository.save(electronic);

            Truck t1 = new Truck();
            t1.setTruckNumber(1);
            t1.setPlateNumber("RAC123A");
            t1 = truckRepository.save(t1);

            Truck t2 = new Truck();
            t2.setTruckNumber(2);
            t2.setPlateNumber("RAC456B");
            t2 = truckRepository.save(t2);

            Truck t3 = new Truck();
            t3.setTruckNumber(3);
            t3.setPlateNumber("RAC789C");
            t3 = truckRepository.save(t3);

            Truck t4 = new Truck();
            t4.setTruckNumber(4);
            t4.setPlateNumber("RAC000D");
            t4 = truckRepository.save(t4);

            Collector c1 = new Collector();
            c1.setFirstName("Eric");
            c1.setLastName("Rukundo");
            c1.setPhone("0788666666");
            c1.setTruck(t1);
            c1.setWasteTypes(new ArrayList<>(List.of(organic, plastic)));
            c1 = collectorRepository.save(c1);

            Collector c2 = new Collector();
            c2.setFirstName("Alice");
            c2.setLastName("Mugabo");
            c2.setPhone("0788777777");
            c2.setTruck(t2);
            c2.setWasteTypes(new ArrayList<>(List.of(glass)));
            c2 = collectorRepository.save(c2);

            Collector c3 = new Collector();
            c3.setFirstName("John");
            c3.setLastName("Bizimana");
            c3.setPhone("0788888888");
            c3.setTruck(t3);
            c3.setWasteTypes(new ArrayList<>());
            c3 = collectorRepository.save(c3);

            Household h1 = householdRepository.save(createHousehold("Jean", "Mugisha", "KCK-GTG-0001", "Gatenga", "0788111111"));
            Household h2 = householdRepository.save(createHousehold("Alice", "Uwase", "KCK-GTG-0045", "Gatenga", "0788222222"));
            Household h3 = householdRepository.save(createHousehold("Paul", "Kamanzi", "KCK-NYR-0010", "Nyarugenge", "0788333333"));
            Household h4 = householdRepository.save(createHousehold("Marie", "Keza", "KCK-KIC-0022", "Kicukiro", "0788444444"));
            Household h5 = householdRepository.save(createHousehold("Claude", "Gakwaya", "KCK-KIC-0099", "Kicukiro", "0788555555"));

            
            Pickup p1 = createPickup(LocalDate.of(2026, 10, 20), 3, h1, c1, PickupStatus.COLLECTED);
            p1 = pickupRepository.save(p1);

            Pickup p2 = createPickup(LocalDate.of(2026, 10, 20), 2, h2, c1, PickupStatus.SKIPPED);
            pickupRepository.save(p2);

            Pickup p3 = createPickup(LocalDate.of(2026, 10, 25), 4, h1, c2, PickupStatus.COLLECTED);
            p3 = pickupRepository.save(p3);

            Pickup p4 = createPickup(LocalDate.of(2026, 11, 5), 1, h1, c3, PickupStatus.REQUESTED);
            pickupRepository.save(p4);

            Pickup p5 = createPickup(LocalDate.of(2026, 11, 4), 2, h3, c2, PickupStatus.REQUESTED);
            pickupRepository.save(p5);

            Pickup p6 = createPickup(LocalDate.of(2026, 11, 3), 2, h5, c3, PickupStatus.REQUESTED);
            pickupRepository.save(p6);

            Pickup p7 = createPickup(LocalDate.of(2026, 11, 2), 5, h2, c3, PickupStatus.REQUESTED);
            pickupRepository.save(p7);

            Pickup p8 = createPickup(LocalDate.of(2026, 11, 1), 1, h3, c1, PickupStatus.REQUESTED);
            pickupRepository.save(p8);

            Pickup p9 = createPickup(LocalDate.of(2026, 10, 30), 3, h4, c2, PickupStatus.ASSIGNED);
            pickupRepository.save(p9);

            Pickup p10 = createPickup(LocalDate.of(2026, 10, 29), 2, h5, c1, PickupStatus.REQUESTED);
            pickupRepository.save(p10);

            
            DisposalRecord dr1 = new DisposalRecord();
            dr1.setPickup(p1);
            dr1.setWasteName("Plastic bottles");
            dr1.setNotes("Delivered to the Nduba recycling point");
            dr1.setWeightKg(150);
            dr1.setDisposedDate(LocalDate.of(2026, 10, 20));
            disposalRecordRepository.save(dr1);

           
            DisposalRecord dr2 = new DisposalRecord();
            dr2.setPickup(p3);
            dr2.setWasteName("Glass bottles & jars");
            dr2.setNotes("Delivered to Nduba sorting facility");
            dr2.setWeightKg(200);
            dr2.setDisposedDate(LocalDate.of(2026, 10, 25));
            disposalRecordRepository.save(dr2);
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

    private Pickup createPickup(LocalDate date, Integer bags, Household household, Collector collector, PickupStatus status) {
        Pickup p = new Pickup();
        p.setPickupDate(date);
        p.setBags(bags);
        p.setHousehold(household);
        p.setCollector(collector);
        p.setStatus(status);
        return p;
    }
}