package com.epam.models;

import javax.swing.*;
import java.util.List;

public interface GameFieldCharacter {

    int getX();

    int getY();

    int getHappiness();

    int getHappinessMin();

    int getHappinessMax();

    int getFullness();

    int getFullnessMin();

    int getFullnessMax();

    ImageIcon getCurrentIcon();

    List<ImageIcon> getIcons();

    Age getAge();

    void moveUp(int step);

    void moveDown(int step);

    void moveLeft(int step);

    void moveRight(int step);

    void eat(int increaseHappinessValue, int increaseFullnessValue);

    void play(int increaseHappinessValue);

    void reduceIndicators(int happinessValue, int fullnessValue);

    void changeAge(ImageIcon icon);
}
