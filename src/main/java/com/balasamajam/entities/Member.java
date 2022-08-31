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

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private String email;

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @PrePersist
    private void onCreate()
    {
        String combo = getFirstName() + getLastName() + getPhone();
        byte [] comboBytes = combo.getBytes();
        setUuid(UUID.nameUUIDFromBytes(comboBytes));
    }
}
