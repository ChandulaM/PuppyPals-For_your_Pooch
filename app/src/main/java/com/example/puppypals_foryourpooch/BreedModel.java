/*IT19149318
 * Dharmasinghe P.D.G.N.T.D.
 * KDY_WD03*/
package com.example.puppypals_foryourpooch;

import java.io.Serializable;

public class BreedModel implements Serializable {
    private String breedId;
    private String breedName;
    private Integer height;
    private Integer weight;
    private Integer lifeSpan;
    private String adaptability;
    private String intelligence;
    private String feedings;
    private String health;
    private String link;
    private String breedImage;
    private int img;

    public void setImg(int img) {
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public BreedModel(String breedName, Integer height, Integer weight, Integer lifeSpan, String adaptability, String intelligence, String feedings, String health, String link, String breedImage, int img) {
        this.breedName = breedName;
        this.height = height;
        this.weight = weight;
        this.lifeSpan = lifeSpan;
        this.adaptability = adaptability;
        this.intelligence = intelligence;
        this.feedings = feedings;
        this.health = health;
        this.link = link;
        this.breedImage = breedImage;
        this.img = img;
    }

    public BreedModel() {
    }

    public BreedModel(String breedId, String breedName, Integer height, Integer weight, Integer lifeSpan, String adaptability, String intelligence, String feedings, String health, String link, String breedImage) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.height = height;
        this.weight = weight;
        this.lifeSpan = lifeSpan;
        this.adaptability = adaptability;
        this.intelligence = intelligence;
        this.feedings = feedings;
        this.health = health;
        this.link = link;
        this.breedImage = breedImage;
    }

    public BreedModel(String breedName, Integer height, Integer weight, Integer lifeSpan, String adaptability, String intelligence, String feedings, String health, String link, String breedImage) {
        this.breedName = breedName;
        this.height = height;
        this.weight = weight;
        this.lifeSpan = lifeSpan;
        this.adaptability = adaptability;
        this.intelligence = intelligence;
        this.feedings = feedings;
        this.health = health;
        this.link = link;
        this.breedImage = breedImage;
    }

    public BreedModel(String breedName) {
        this.breedName = breedName;
    }

    public String getBreedId() {
        return breedId;
    }

    public String getBreedName() {
        return breedName;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getLifeSpan() {
        return lifeSpan;
    }

    public String getAdaptability() {
        return adaptability;
    }

    public String getIntelligence() {
        return intelligence;
    }

    public String getFeedings() {
        return feedings;
    }

    public String getHealth() {
        return health;
    }

    public String getLink() {
        return link;
    }

    public String getBreedImage() {
        return breedImage;
    }


    public void setBreedId(String breedId) {
        this.breedId = breedId;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setLifeSpan(Integer lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public void setAdaptability(String adaptability) {
        this.adaptability = adaptability;
    }

    public void setIntelligence(String intelligence) {
        this.intelligence = intelligence;
    }

    public void setFeedings(String feedings) {
        this.feedings = feedings;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setBreedImage(String breedImage) {
        this.breedImage = breedImage;
    }

    @Override
    public String toString() {
        return "BreedModel{" +
                "breedId='" + breedId + '\'' +
                ", breedName='" + breedName + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", lifeSpan=" + lifeSpan +
                ", adaptability='" + adaptability + '\'' +
                ", intelligence='" + intelligence + '\'' +
                ", feedings='" + feedings + '\'' +
                ", health='" + health + '\'' +
                ", link='" + link + '\'' +
                ", breedImage='" + breedImage + '\'' +
                '}';
    }
}
