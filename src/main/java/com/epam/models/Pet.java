package com.epam.models;

import javax.swing.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Pet implements GameFieldCharacter, Serializable {
    private final int MIN_HAPPINESS_VALUE = 0;
    private final int MAX_HAPPINESS_VALUE = 10;
    private final int MIN_FULLNESS_VALUE = 0;
    private final int MAX_FULLNESS_VALUE = 10;

    private Integer x;
    private Integer y;
    private Integer happiness;
    private Integer fullness;
    private Age age;
    private ImageIcon currentIcon;
    private List<ImageIcon> icons;

    public Pet(int coord) {
        x = coord;
        y = coord;
        happiness = MAX_HAPPINESS_VALUE;
        fullness = MAX_FULLNESS_VALUE;
        age = Age.TEEN;
    }

    public Pet(Pet pet) {
        this.x = pet.getX();
        this.y = pet.getY();
        this.happiness = pet.getHappiness();
        this.fullness = pet.getFullness();
        this.age = pet.getAge();
        this.currentIcon = pet.getCurrentIcon();
        this.icons = pet.getIcons();
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
    public int getHappiness() {
        return happiness;
    }

    @Override
    public int getHappinessMin() {
        return MIN_HAPPINESS_VALUE;
    }

    @Override
    public int getHappinessMax() {
        return MAX_HAPPINESS_VALUE;
    }

    @Override
    public int getFullness() {
        return fullness;
    }

    @Override
    public int getFullnessMin() {
        return MIN_FULLNESS_VALUE;
    }

    @Override
    public int getFullnessMax() {
        return MAX_FULLNESS_VALUE;
    }

    @Override
    public Age getAge() {
        return age;
    }

    @Override
    public ImageIcon getCurrentIcon() {
        return currentIcon;
    }

    @Override
    public List<ImageIcon> getIcons() {
        return icons;
    }

    @Override
    public void moveUp(int step) {
        y -= step;
    }

    @Override
    public void moveDown(int step) {
        y += step;
    }

    @Override
    public void moveLeft(int step) {
        x -=step;
    }

    @Override
    public void moveRight(int step) {
        x += step;
    }

    @Override
    public void eat(int increaseHappinessValue, int increaseFullnessValue) {
        if(happiness <= MAX_HAPPINESS_VALUE - increaseHappinessValue) {
            happiness += increaseHappinessValue;
        }

        if(fullness <= MAX_FULLNESS_VALUE - increaseFullnessValue) {
            fullness += increaseFullnessValue;
        }
    }

    @Override
    public void play(int increaseHappinessValue) {
        if(happiness <= MAX_HAPPINESS_VALUE - increaseHappinessValue) {
            happiness += increaseHappinessValue;
        }
    }

    @Override
    public void reduceIndicators(int happinessValue, int fullnessValue) {
        if(happiness > MIN_HAPPINESS_VALUE) {
            happiness -= happinessValue;
        }

        if(fullness > MIN_FULLNESS_VALUE) {
            fullness -= fullnessValue;
        }
    }

    @Override
    public void changeAge(ImageIcon icon) {
        Age[] ages = Age.values();

        if(age.ordinal() < ages.length - 1) {
            age = ages[age.ordinal() + 1];
            currentIcon = icon;
        }
    }

    public void setCurrentIcon(ImageIcon icon) {
        this.currentIcon = icon;
    }

    public void setIcons(List<ImageIcon> icons) {
        this.icons = icons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return Objects.equals(x, pet.x) &&
                Objects.equals(y, pet.y) &&
                Objects.equals(happiness, pet.happiness) &&
                Objects.equals(fullness, pet.fullness) &&
                age == pet.age &&
                Objects.equals(currentIcon, pet.currentIcon) &&
                Objects.equals(icons, pet.icons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, happiness, fullness, age, currentIcon, icons);
    }
}
