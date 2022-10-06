package com.balasamajam.models;

public class SearchMemberResponseModel
{
    private String memberFullName;

    private String memberLocalName;

    private double maranavari;

    private double masavari;

    private double total;

    private String phoneNumber;

    public String getMemberFullName() {
        return memberFullName;
    }

    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }

    public String getMemberLocalName() {
        return memberLocalName;
    }

    public void setMemberLocalName(String memberLocalName) {
        this.memberLocalName = memberLocalName;
    }

    public double getMaranavari() {
        return maranavari;
    }

    public void setMaranavari(double maranavari) {
        this.maranavari = maranavari;
    }

    public double getMasavari() {
        return masavari;
    }

    public void setMasavari(double masavari) {
        this.masavari = masavari;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
