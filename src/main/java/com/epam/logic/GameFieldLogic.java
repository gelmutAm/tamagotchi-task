package com.epam.logic;

import com.epam.models.*;

import javax.swing.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class GameFieldLogic implements GameFieldLogicInterface, Serializable {

    private GameFieldCharacter character;
    private GameFieldFood food;
    private GameFieldToy toy;

    public GameFieldCharacter getCharacter() {
        return character;
    }

    public GameFieldFood getFood() {
        return food;
    }

    public GameFieldToy getToy() {
        return toy;
    }

    public int getRandomCoord(int gameFieldSize, int cellSize) {
        Random random = new Random();
        int value = random.nextInt(gameFieldSize - cellSize);
        value /= cellSize;
        return value * cellSize;
    }

    public void createCharacter(String iconFileName, int cellSize, int shift) {
        if(character == null) {
            int coord = cellSize * shift;
            Pet pet = new Pet(coord);
            pet.setIcon(new ImageIcon(iconFileName));
            character = pet;
        }
    }

    public void createFood(String iconFileName, int increaseHappinessValue, int increaseFullnessValue, int gameFieldSize, int cellSize) {
        if(food == null) {
            int coord = getRandomCoord(gameFieldSize, cellSize);
            Food food = new Food(coord, increaseHappinessValue, increaseFullnessValue);

            while (food.getX() == character.getX() && food.getY() == character.getY()) {
                coord = getRandomCoord(gameFieldSize, cellSize);
                food = new Food(coord, increaseHappinessValue, increaseFullnessValue);
            }

            food.setIcon(new ImageIcon(iconFileName));
            this.food = food;
        }
    }

    public void moveUp(int cellSize, int minCoord) {
        if (character.getY() > minCoord) {
            character.moveUp(cellSize);
        }
    }

    public void moveDown(int cellSize, int maxCoord) {
        if (character.getY() < maxCoord) {
            character.moveDown(cellSize);
        }
    }

    public void moveLeft(int cellSize, int minCoord) {
        if (character.getX() > minCoord) {
            character.moveLeft(cellSize);
        }
    }

    public void moveRight(int cellSize, int maxCoord) {
        if (character.getX() < maxCoord) {
            character.moveRight(cellSize);
        }
    }

    public boolean feed() {
        if(food != null) {
            if (food.getX() == character.getX() && food.getY() == character.getY()) {
                character.eat(food.getIncreaseHappinessValue(), food.getIncreaseFullnessValue());
                food = null;

                return true;
            }
        }

        return false;
    }

    public void play(String iconFileName, int increaseHappinessValue, int gameFieldSize, int cellSize) {
        if(character != null) {
            int coord = getRandomCoord(gameFieldSize, cellSize);
            toy = new Toy(coord, increaseHappinessValue, new ImageIcon(iconFileName));

            while ((character.getX() == toy.getX() && character.getY() == toy.getY())) {
                coord = getRandomCoord(gameFieldSize, cellSize);
                toy = new Toy(coord, increaseHappinessValue, new ImageIcon(iconFileName));
            }

            character.play(increaseHappinessValue);
        }
    }

    public void reduceIndicators(int happinessValue, int fullnessValue) {
        character.reduceIndicators(happinessValue, fullnessValue);
    }

    public void setCharacterToNull() {
        character = null;
    }

    public void setFoodToNull() {
        food = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameFieldLogic)) return false;
        GameFieldLogic that = (GameFieldLogic) o;
        return Objects.equals(character, that.character) &&
                Objects.equals(food, that.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, food);
    }
}