package com.balasamajam.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.UUID;

@Entity
public class Member
{
    @Id
    @Column(nullable = false)
    private UUID uuid;

    private String fullName;

    private String localizedFullName;

    private String address;

    private String phone;

    private String email;

    private double maranavari;

    private double masavari;

    private double total;

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getLocalizedFullName() {
        return localizedFullName;
    }

    public void setLocalizedFullName(String localizedFullName) {
        this.localizedFullName = localizedFullName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public double getMaranavari() {
        return maranavari;
    }

    public void setMaranavari(double maranavari) {
        this.maranavari = maranavari;
    }

    public double getMasavari() {
        return masavari;
    }

    public void setMasavari(double masavari) {
        this.masavari = masavari;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @PrePersist
    private void onCreate()
    {
        String combo = getFullName() + getPhone();
        byte [] comboBytes = combo.getBytes();
        setUuid(UUID.nameUUIDFromBytes(comboBytes));
    }
}
