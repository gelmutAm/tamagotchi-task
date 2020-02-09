package com.epam.logic;

import com.epam.models.GameFieldCharacter;
import com.epam.models.GameFieldFood;
import com.epam.models.GameFieldToy;

public interface GameFieldLogicInterface {

    GameFieldCharacter getCharacter();

    GameFieldFood getFood();

    GameFieldToy getToy();

    void createCharacter(String iconFileName, int cellSize, int shift);

    void createFood(String iconFileName, int increaseHappinessValue, int increaseFullnessValue, int gameFieldSize, int cellSize);

    void moveUp(int cellSize, int minCoord);

    void moveDown(int cellSize, int maxCoord);

    void moveLeft(int cellSize, int minCoord);

    void moveRight(int cellSize, int maxCoord);

    boolean feed();

    void reduceIndicators(int happinessValue, int fullnessValue);

    void play(String iconFileName, int increaseHappinessValue, int gameFieldSize, int cellSize);

    void setCharacterToNull();

    void setFoodToNull();
}
