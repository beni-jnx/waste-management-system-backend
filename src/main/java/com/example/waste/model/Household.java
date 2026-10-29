package com.example.waste.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "household")
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String headFirstName;
    private String headLastName;

    @Column(unique = true)
    private String houseCode;

    private String village;
    private String phone;

    public Household() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getHeadFirstName() { return headFirstName; }
    public void setHeadFirstName(String headFirstName) { this.headFirstName = headFirstName; }

    public String getHeadLastName() { return headLastName; }
    public void setHeadLastName(String headLastName) { this.headLastName = headLastName; }

    public String getHouseCode() { return houseCode; }
    public void setHouseCode(String houseCode) { this.houseCode = houseCode; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}