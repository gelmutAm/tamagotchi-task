package com.epam.models;

import javax.swing.*;
import java.io.Serializable;

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
}
