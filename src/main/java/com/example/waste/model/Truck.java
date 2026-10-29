package com.example.waste.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "truck")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int truckNumber;
    private String plateNumber;

    public Truck() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public int getTruckNumber() { return truckNumber; }
    public void setTruckNumber(int truckNumber) { this.truckNumber = truckNumber; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
}