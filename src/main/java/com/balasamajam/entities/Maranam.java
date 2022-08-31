package com.balasamajam.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Maranam
{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "relative_of")
    private Member relativeOf;

    private Timestamp dateOfDeath;

    private Float totalCost;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member getRelativeOf() {
        return relativeOf;
    }

    public void setRelativeOf(Member relativeOf) {
        this.relativeOf = relativeOf;
    }

    public Timestamp getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Timestamp dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
