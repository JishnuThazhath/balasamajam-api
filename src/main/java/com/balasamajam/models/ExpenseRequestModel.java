package com.balasamajam.models;

import com.balasamajam.constants.ExpenseType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ExpenseRequestModel
{
    private String expenseDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date expenseDate;

    private Float expenseAmount;

    private ExpenseType expenseType;

    private String note;

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Float getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Float expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
