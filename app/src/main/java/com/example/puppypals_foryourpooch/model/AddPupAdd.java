package com.example.puppypals_foryourpooch.model;

public class AddPupAdd {

    private String userId;
    private String breed;
    private String phone;
    private float price;
    private String imageUri;

    public AddPupAdd(){


    }

    public AddPupAdd(String userId, String breed, String phone, float price, String imageUri) {

        this.userId = userId;
        this.breed = breed;
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
