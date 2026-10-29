package com.example.waste.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "disposal_record")
public class DisposalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String wasteName;
    private String notes;
    private int weightKg;
    private LocalDate disposedDate;

    @ManyToOne
    @JoinColumn(name = "pickup_id")
    private Pickup pickup;

    public DisposalRecord() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getWasteName() { return wasteName; }
    public void setWasteName(String wasteName) { this.wasteName = wasteName; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getWeightKg() { return weightKg; }
    public void setWeightKg(int weightKg) { this.weightKg = weightKg; }

    public LocalDate getDisposedDate() { return disposedDate; }
    public void setDisposedDate(LocalDate disposedDate) { this.disposedDate = disposedDate; }

    public Pickup getPickup() { return pickup; }
    public void setPickup(Pickup pickup) { this.pickup = pickup; }
}