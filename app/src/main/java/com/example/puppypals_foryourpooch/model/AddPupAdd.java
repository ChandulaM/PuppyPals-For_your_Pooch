package com.example.puppypals_foryourpooch.model;

public class AddPupAdd {

    private String userId;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    private String adId;
    private String breed;
    private String email;
    private String phone;
    private float price;
    private String imageUri;

    public AddPupAdd(){


    }

    public AddPupAdd(String userId, String breed, String email, String phone, float price, String imageUri) {

        this.userId = userId;
        this.breed = breed;
        this.email = email;
        this.phone = phone;
        this.price = price;
        this.imageUri = imageUri;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
