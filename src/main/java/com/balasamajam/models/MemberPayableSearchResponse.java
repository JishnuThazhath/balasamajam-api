package com.balasamajam.models;

public class MemberPayableSearchResponse
{
    private String name;
    private Double totalMaranavariAmount;
    private Double totalMasavariAmount;
    private Double totalPayableAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalMaranavariAmount()
    {
        return totalMaranavariAmount;
    }

    public void setTotalMaranavariAmount(Double totalMaranavariAmount)
    {
        this.totalMaranavariAmount = totalMaranavariAmount;
    }

    public Double getTotalMasavariAmount()
    {
        return totalMasavariAmount;
    }

    public void setTotalMasavariAmount(Double totalMasavariAmount)
    {
        this.totalMasavariAmount = totalMasavariAmount;
    }

    public Double getTotalPayableAmount()
    {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(Double totalPayableAmount)
    {
        this.totalPayableAmount = totalPayableAmount;
    }
}
