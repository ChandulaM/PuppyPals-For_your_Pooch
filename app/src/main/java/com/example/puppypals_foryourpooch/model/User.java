package com.example.puppypals_foryourpooch.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String email;
    private String username;
    private String password;
    private String imageUrl;
    private double latitude;
    private double longitude;

    public User() {
    }

    public User(String userId, String email, String username, String password, String imageUrl, double latitude, double longitude) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId;  }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
