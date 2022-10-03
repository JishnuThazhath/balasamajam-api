package com.balasamajam.entities;

import javax.persistence.*;

@Entity
public class Expense
{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;

    private double amount;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "added_by_uuid")
    private Admin added_by;

    @ManyToOne
    @JoinColumn(name = "relation_to_member_uuid")
    private Member relationToMember;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Admin getAdded_by() {
        return added_by;
    }

    public void setAdded_by(Admin added_by) {
        this.added_by = added_by;
    }

    public Member getRelationToMember() {
        return relationToMember;
    }

    public void setRelationToMember(Member relationToMember) {
        this.relationToMember = relationToMember;
    }
}
