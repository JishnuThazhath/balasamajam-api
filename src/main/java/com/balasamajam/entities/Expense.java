package com.balasamajam.entities;

import com.balasamajam.constants.ExpenseType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Expense
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    private double amount;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "added_by_uuid")
    private Admin addedBy;

    @ManyToOne
    @JoinColumn(name = "relation_to_member_uuid")
    private Member relationToMember;

    private ExpenseType expenseType;

    private Timestamp date;

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

    public Admin getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Admin addedBy) {
        this.addedBy = addedBy;
    }

    public Member getRelationToMember() {
        return relationToMember;
    }

    public void setRelationToMember(Member relationToMember) {
        this.relationToMember = relationToMember;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
