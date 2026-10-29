package com.example.waste.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "collector")
public class Collector {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @ManyToMany
    @JoinTable(
        name = "collector_waste_type",
        joinColumns = @JoinColumn(name = "collector_id"),
        inverseJoinColumns = @JoinColumn(name = "waste_type_id")
    )
    private List<WasteType> wasteTypes;

    public Collector() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Truck getTruck() { return truck; }
    public void setTruck(Truck truck) { this.truck = truck; }

    public List<WasteType> getWasteTypes() { return wasteTypes; }
    public void setWasteTypes(List<WasteType> wasteTypes) { this.wasteTypes = wasteTypes; }
}