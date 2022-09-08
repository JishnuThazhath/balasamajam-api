package com.balasamajam.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Admin {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private String username;

    private String password;

    protected Admin() {}

    public Admin(UUID id, String firstName, String lastName, String address, String phone) {
        this.uuid = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PrePersist
    private void onCreate() {
        String combo = getFirstName() + getLastName() + getPhone();
        byte [] comboBytes = combo.getBytes();
        setUuid(UUID.nameUUIDFromBytes(comboBytes));
    }
}
