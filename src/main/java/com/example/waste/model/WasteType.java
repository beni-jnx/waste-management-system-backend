package com.example.waste.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "waste_type")
public class WasteType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "wasteTypes")
    @JsonIgnore
    private List<Collector> collectors;

    public WasteType() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Collector> getCollectors() { return collectors; }
    public void setCollectors(List<Collector> collectors) { this.collectors = collectors; }
}