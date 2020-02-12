package com.epam.models;

import javax.swing.*;
import java.io.Serializable;
import java.util.Objects;

public class Toy implements GameFieldToy, Serializable {
    private Integer x;
    private Integer y;
    private Integer increaseHappinessValue;
    private ImageIcon icon;

    public Toy(int coord, int increaseHappinessValue, ImageIcon icon) {
        x = coord;
        y = coord;
        this.increaseHappinessValue = increaseHappinessValue;
        this.icon = icon;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getIncreaseHappinessValue() {
        return increaseHappinessValue;
    }

    @Override
    public ImageIcon getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Toy)) return false;
        Toy toy = (Toy) o;
        return Objects.equals(x, toy.x) &&
                Objects.equals(y, toy.y) &&
                Objects.equals(increaseHappinessValue, toy.increaseHappinessValue) &&
                Objects.equals(icon, toy.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, increaseHappinessValue, icon);
    }
}
