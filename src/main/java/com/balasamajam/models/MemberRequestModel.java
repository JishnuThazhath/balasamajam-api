package com.balasamajam.models;

public class MemberRequestModel
{
    private String fullName;

    private String address;

    private String phone;

    private String email;

    private String localizedFullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalizedFullName() {
        return localizedFullName;
    }

    public void setLocalizedFullName(String localizedFullName) {
        this.localizedFullName = localizedFullName;
    }
}
