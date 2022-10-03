package com.balasamajam.models;

import com.balasamajam.constants.ExpenseType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AddExpenseRequestModel
{
    // This field can be the name of the dead.
    private String description;

    private String memberId;

    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfDeath;

    private String notes;

    private String addedByAdminId;

    private ExpenseType type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddedByAdminId() {
        return addedByAdminId;
    }

    public void setAddedByAdminId(String addedByAdminId) {
        this.addedByAdminId = addedByAdminId;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }
}
