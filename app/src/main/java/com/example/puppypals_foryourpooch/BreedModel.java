package com.example.puppypals_foryourpooch;

import java.io.Serializable;

public class BreedModel implements Serializable {
    private String breedName;
    private int height;
    private int weight;
    private int lSpan;
    private String adap;
    private String intell;
    private String feed;
    private String health;
    private String link;
    private String bImage;

    public BreedModel() {
    }

    public BreedModel(String breedName, int height, int weight, int lSpan, String adap, String intell, String feed, String health, String link, String bImage) {
        this.breedName = breedName;
        this.height = height;
        this.weight = weight;
        this.lSpan = lSpan;
        this.adap = adap;
        this.intell = intell;
        this.feed = feed;
        this.health = health;
        this.link = link;
        this.bImage = bImage;
    }

    public BreedModel(String breedName) {
        this.breedName = breedName;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getlSpan() {
        return lSpan;
    }

    public void setlSpan(int lSpan) {
        this.lSpan = lSpan;
    }

    public String getAdap() {
        return adap;
    }

    public void setAdap(String adap) {
        this.adap = adap;
    }

    public String getIntell() {
        return intell;
    }

    public void setIntell(String intell) {
        this.intell = intell;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getbImage() {
        return bImage;
    }

    public void setbImage(String bImage) {
        this.bImage = bImage;
    }
}
