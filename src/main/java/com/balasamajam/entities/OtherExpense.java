package com.balasamajam.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class OtherExpense {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;

    private Timestamp dateOfExpense;

    private String amount;

    private String note;

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

    public Timestamp getDateOfExpense() {
        return dateOfExpense;
    }

    public void setDateOfExpense(Timestamp dateOfExpense) {
        this.dateOfExpense = dateOfExpense;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
