package com.epam.models;

import java.awt.*;

public class Pet {
    private Integer hungriness;
    private Integer happiness;
    private Image petPic;

    public Pet() {
    }

    public Pet(Image petPic) {
        this.hungriness = 10;
        this.happiness = 10;
        this.petPic = petPic;
    }

    public Pet(Integer hungriness, Integer happiness) {
        this.hungriness = hungriness;
        this.happiness = happiness;
    }

    public Integer getHungriness() {
        return hungriness;
    }

    public void setHungriness(Integer hungriness) {
        this.hungriness = hungriness;
    }

    public Integer getHappiness() {
        return happiness;
    }

    public void setHappiness(Integer happiness) {
        this.happiness = happiness;
    }

    public Image getPetPic() {
        return petPic;
    }

    public void setPetPic(Image petPic) {
        this.petPic = petPic;
    }
}
