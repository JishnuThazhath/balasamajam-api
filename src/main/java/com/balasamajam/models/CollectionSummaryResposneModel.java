package com.balasamajam.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CollectionSummaryResposneModel
{
    private String collectedByFullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date paymentDate;

    private String collectedFromFullName;

    private double paidAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date endDate;

    public String getCollectedByFullName() {
        return collectedByFullName;
    }

    public void setCollectedByFullName(String collectedByFullName) {
        this.collectedByFullName = collectedByFullName;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCollectedFromFullName() {
        return collectedFromFullName;
    }

    public void setCollectedFromFullName(String collectedFromFullName) {
        this.collectedFromFullName = collectedFromFullName;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
