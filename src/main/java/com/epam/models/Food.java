package com.epam.models;

import javax.swing.*;
import java.io.Serializable;
import java.util.Objects;

public class Food implements GameFieldFood, Serializable {
    private Integer x;
    private Integer y;
    private Integer increaseHappinessValue;
    private Integer increaseFullnessValue;
    private ImageIcon icon;

    public Food(int coord, int increaseHappinessValue, int increaseFullnessValue) {
        x = coord;
        y = coord;
        this.increaseHappinessValue = increaseHappinessValue;
        this.increaseFullnessValue = increaseFullnessValue;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIncreaseHappinessValue() {
        return increaseHappinessValue;
    }

    public int getIncreaseFullnessValue() {
        return increaseFullnessValue;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
