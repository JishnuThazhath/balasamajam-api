package com.balasamajam.models;

public class LoginResponse extends Output {
    private String token;

    public LoginResponse(String token, String status) {
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
