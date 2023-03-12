package com.balasamajam.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AddMasavariRequestModel
{
    private double total;

    private double paidAmount;

    private double maranavariAmount;

    private double masavariAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;

    private String memberId;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getMaranavariAmount() {
        return maranavariAmount;
    }

    public void setMaranavariAmount(double maranavariAmount) {
        this.maranavariAmount = maranavariAmount;
    }

    public double getMasavariAmount() {
        return masavariAmount;
    }

    public void setMasavariAmount(double masavariAmount) {
        this.masavariAmount = masavariAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
