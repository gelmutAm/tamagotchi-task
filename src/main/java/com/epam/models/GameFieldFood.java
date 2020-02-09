package com.epam.models;

import javax.swing.*;

public interface GameFieldFood {

    int getX();

    int getY();

    int getIncreaseHappinessValue();

    int getIncreaseFullnessValue();

    ImageIcon getIcon();
}
