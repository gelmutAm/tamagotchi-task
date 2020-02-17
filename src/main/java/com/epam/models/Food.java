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

    public Food(int increaseHappinessValue, int increaseFullnessValue) {
        this.increaseHappinessValue = increaseHappinessValue;
        this.increaseFullnessValue = increaseFullnessValue;
    }

    public int getX() {
        return x;
    }

    public void setX(int value) {
        x = value;
    }

    public int getY() {
        return y;
    }

    public void setY(int value) {
        y = value;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food food = (Food) o;
        return Objects.equals(x, food.x) &&
                Objects.equals(y, food.y) &&
                Objects.equals(increaseHappinessValue, food.increaseHappinessValue) &&
                Objects.equals(increaseFullnessValue, food.increaseFullnessValue) &&
                Objects.equals(icon, food.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, increaseHappinessValue, increaseFullnessValue, icon);
    }
}
