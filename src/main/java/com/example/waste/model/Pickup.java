package com.example.waste.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pickup")
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate pickupDate;

    @Enumerated(EnumType.STRING)
    private PickupStatus status;

    private int bags;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    @ManyToOne
    @JoinColumn(name = "collector_id")
    private Collector collector;

    public Pickup() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDate getPickupDate() { return pickupDate; }
    public void setPickupDate(LocalDate pickupDate) { this.pickupDate = pickupDate; }

    public PickupStatus getStatus() { return status; }
    public void setStatus(PickupStatus status) { this.status = status; }

    public int getBags() { return bags; }
    public void setBags(int bags) { this.bags = bags; }

    public Household getHousehold() { return household; }
    public void setHousehold(Household household) { this.household = household; }

    public Collector getCollector() { return collector; }
    public void setCollector(Collector collector) { this.collector = collector; }
}