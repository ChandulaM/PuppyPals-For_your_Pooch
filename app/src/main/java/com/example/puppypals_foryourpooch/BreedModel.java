package com.example.puppypals_foryourpooch;

import java.io.Serializable;

public class BreedModel implements Serializable {
    private String breedName;

    public BreedModel(String breedName) {
        this.breedName = breedName;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }
}
