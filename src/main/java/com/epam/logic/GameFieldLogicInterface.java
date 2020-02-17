package com.epam.logic;

import com.epam.models.GameFieldCharacter;
import com.epam.models.GameFieldFood;

import javax.swing.*;
import java.util.List;

public interface GameFieldLogicInterface {

    GameFieldCharacter getCharacter();

    GameFieldFood getFood();

    void createCharacter(List<ImageIcon> icons, int cellSize, int shift);

    void createFood(String iconFileName, int increaseHappinessValue, int increaseFullnessValue, int gameFieldSize, int cellSize);

    void moveUp(int cellSize, int minCoord);

    void moveDown(int cellSize, int maxCoord);

    void moveLeft(int cellSize, int minCoord);

    void moveRight(int cellSize, int maxCoord);

    boolean feed();

    void reduceIndicators(int happinessValue, int fullnessValue);

    void changeAge();

    void setCharacterToNull();

    void setFoodToNull();

    int getIconIndex();
}
