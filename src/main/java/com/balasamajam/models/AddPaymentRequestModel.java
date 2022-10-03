package com.balasamajam.models;

public class AddPaymentRequestModel
{
    private String memberId;

    private String collectedById;

    private double amount;

    private String comments;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCollectedById() {
        return collectedById;
    }

    public void setCollectedById(String collectedById) {
        this.collectedById = collectedById;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
