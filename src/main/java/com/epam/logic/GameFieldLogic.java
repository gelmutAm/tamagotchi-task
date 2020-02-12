package com.epam.logic;

import com.epam.models.*;

import javax.swing.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameFieldLogic implements GameFieldLogicInterface, Serializable {

    private GameFieldCharacter character;
    private GameFieldFood food;
    private GameFieldToy toy;

    private int iconIndex = 0;

    public GameFieldLogic() {
    }

    public GameFieldLogic(GameFieldLogicInterface gameFieldLogic) {
        this();

        if(gameFieldLogic.getCharacter() != null) {
            this.character = new Pet((Pet) gameFieldLogic.getCharacter());
            this.food = gameFieldLogic.getFood();
            this.toy = gameFieldLogic.getToy();
            this.iconIndex = gameFieldLogic.getIconIndex();
        }
    }

    @Override
    public GameFieldCharacter getCharacter() {
        return character;
    }

    @Override
    public GameFieldFood getFood() {
        return food;
    }

    @Override
    public GameFieldToy getToy() {
        return toy;
    }

    private int getRandomCoord(int gameFieldSize, int cellSize) {
        Random random = new Random();
        int value = random.nextInt(gameFieldSize - cellSize);
        value /= cellSize;
        return value * cellSize;
    }

    @Override
    public void createCharacter(List<ImageIcon> icons, int cellSize, int shift) {
        int coord = cellSize * shift;
        Pet pet = new Pet(coord);
        pet.setIcons(icons);
        pet.setCurrentIcon(pet.getIcons().get(iconIndex));
        character = pet;
    }

    @Override
    public void createFood(String iconFileName, int increaseHappinessValue, int increaseFullnessValue, int gameFieldSize, int cellSize) {
        int coord = getRandomCoord(gameFieldSize, cellSize);
        Food food = new Food(coord, increaseHappinessValue, increaseFullnessValue);

        while (food.getX() == character.getX() && food.getY() == character.getY()) {
            coord = getRandomCoord(gameFieldSize, cellSize);
            food = new Food(coord, increaseHappinessValue, increaseFullnessValue);
        }

        food.setIcon(new ImageIcon(iconFileName));
        this.food = food;
    }

    @Override
    public void moveUp(int cellSize, int minCoord) {
        if (character.getY() > minCoord) {
            character.moveUp(cellSize);
        }
    }

    @Override
    public void moveDown(int cellSize, int maxCoord) {
        if (character.getY() < maxCoord) {
            character.moveDown(cellSize);
        }
    }

    @Override
    public void moveLeft(int cellSize, int minCoord) {
        if (character.getX() > minCoord) {
            character.moveLeft(cellSize);
        }
    }

    @Override
    public void moveRight(int cellSize, int maxCoord) {
        if (character.getX() < maxCoord) {
            character.moveRight(cellSize);
        }
    }

    @Override
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

    @Override
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

    @Override
    public void reduceIndicators(int happinessValue, int fullnessValue) {
        character.reduceIndicators(happinessValue, fullnessValue);
    }

    @Override
    public void changeAge() {
        if(iconIndex < character.getIcons().size() - 1) {
            iconIndex++;
            character.changeAge(character.getIcons().get(iconIndex));
        }
    }

    @Override
    public void setCharacterToNull() {
        character = null;
        iconIndex = 0;
    }

    @Override
    public void setFoodToNull() {
        food = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameFieldLogic)) return false;
        GameFieldLogic that = (GameFieldLogic) o;
        return iconIndex == that.iconIndex &&
                Objects.equals(character, that.character) &&
                Objects.equals(food, that.food) &&
                Objects.equals(toy, that.toy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, food, toy, iconIndex);
    }

    @Override
    public int getIconIndex() {
        return iconIndex;
    }
}