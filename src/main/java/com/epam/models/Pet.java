package com.epam.models;

import java.awt.*;

public class Pet {
    private Integer xCoord = 0;
    private Integer yCoord = 0;
    private Integer hungriness;
    private Integer happiness;
    private Image picture;

    public Pet() {
    }

    public Pet(Image picture) {
        this.hungriness = 10;
        this.happiness = 10;
        this.picture = picture;
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

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public Integer getxCoord() {
        return xCoord;
    }

    public void setxCoord(Integer xCoord) {
        this.xCoord = xCoord;
    }

    public Integer getyCoord() {
        return yCoord;
    }

    public void setyCoord(Integer yCoord) {
        this.yCoord = yCoord;
    }
}
