package com.epam.graphics;

import com.epam.logic.GameFieldLogic;
import com.epam.logic.GameFieldLogicInterface;
import com.epam.models.Age;
import com.epam.models.GameFieldCharacter;
import com.epam.models.GameFieldFood;
import com.epam.models.GameFieldToy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class GameField extends JPanel implements Serializable {
    private final int SIZE = 280;
    private final int CELL_SIZE = 35;
    private final int MIN_COORD = 0;
    private final int MAX_COORD = SIZE - CELL_SIZE;
    private final int DELAY = 30000;
    private final ImageIcon RIP = new ImageIcon("rip.png");

    private GameFieldLogicInterface gameFieldLogic;

    private Timer lifeTimer = new Timer(DELAY, null);
    private Timer ageTimer = new Timer(DELAY, null);

    private boolean characterIsDead;
    private int ripX;
    private int ripY;

    private String foodIconFileName;

    public GameField(GameFieldLogicInterface gameFieldLogic) {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.green);
        setBorder(BorderFactory.createLineBorder(Color.magenta));

        this.gameFieldLogic = gameFieldLogic;
    }

    public GameField(GameField gameField) {
        this.gameFieldLogic = new GameFieldLogic(gameField.getGameFieldLogic());
        this.lifeTimer = gameField.getLifeTimer();
        this.ageTimer = gameField.getAgeTimer();
        this.characterIsDead = gameField.characterIsDead();
        this.ripX = gameField.getRipX();
        this.ripY = gameField.getRipY();
        this.foodIconFileName = gameField.getFoodIconFileName();

    }

    public void startTimers(){
        lifeTimer.start();
        ageTimer.start();
    }

    public void initLifeTimer() {
        lifeTimer.addActionListener((ActionEvent e) -> {
            GameFieldCharacter character = gameFieldLogic.getCharacter();
            int happinessValue = 1;
            int fullnessValue = 2;

            if(character != null) {
                if (character.getFullness() > character.getFullnessMin() + fullnessValue) {
                    gameFieldLogic.reduceIndicators(happinessValue, fullnessValue);
                } else {
                    allToStart(character);
                }
            } else {
                characterIsDead = false;
            }

            repaint();
        });
    }

    public void initAgeTimer() {
        ageTimer.addActionListener((ActionEvent e) -> {
            if(gameFieldLogic.getCharacter() != null) {
                if (gameFieldLogic.getCharacter().getAge() == Age.ELDERLY) {
                    allToStart(gameFieldLogic.getCharacter());
                }

                if (gameFieldLogic.getCharacter() != null) {
                    gameFieldLogic.changeAge();
                }

                repaint();
            }
        });
    }

    public boolean characterExists() {
        return gameFieldLogic.getCharacter() != null;
    }

    public boolean characterIsDead() {
        return characterIsDead;
    }

    public void createCharacter(List<ImageIcon> icons) {
        gameFieldLogic.createCharacter(icons, CELL_SIZE, 3);
        startTimers();
    }

    public boolean foodExists() {
        return gameFieldLogic.getFood() != null;
    }

    public void createFood(int increaseHappinessValue, int increaseFullnessValue) {
        gameFieldLogic.createFood(foodIconFileName,
                increaseHappinessValue,
                increaseFullnessValue,
                SIZE,
                CELL_SIZE
        );
    }

    public void moveUp() {
        gameFieldLogic.moveUp(CELL_SIZE, MIN_COORD);
    }

    public void moveDown() {
        gameFieldLogic.moveDown(CELL_SIZE, MAX_COORD);
    }

    public void moveLeft() {
        gameFieldLogic.moveLeft(CELL_SIZE, MIN_COORD);
    }

    public void moveRight() {
        gameFieldLogic.moveRight(CELL_SIZE, MAX_COORD);
    }

    public void feed() {
        if(gameFieldLogic.feed()) {
            lifeTimer.restart();
        }
    }

    public void play(String iconFileName, int increaseHappinessValue) {
        gameFieldLogic.play(iconFileName, increaseHappinessValue, SIZE, CELL_SIZE);
    }

    private void allToStart(GameFieldCharacter character) {
        ripX = character.getX();
        ripY = character.getY();
        gameFieldLogic.setCharacterToNull();
        gameFieldLogic.setFoodToNull();
        characterIsDead = true;
    }

    public void setFoodIconFileName(String fileName) {
        foodIconFileName = fileName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(gameFieldLogic.getCharacter() != null) {
            GameFieldCharacter character = gameFieldLogic.getCharacter();
            Image charIcon = character.getCurrentIcon().getImage();
            int charX = character.getX();
            int charY = character.getY();
            g.drawImage(charIcon, charX, charY, this);

            if(gameFieldLogic.getFood() != null) {
                GameFieldFood food = gameFieldLogic.getFood();
                Image foodIcon = food.getIcon().getImage();
                int foodX = food.getX();
                int foodY = food.getY();
                g.drawImage(foodIcon, foodX, foodY, this);
            }

            if(gameFieldLogic.getToy() != null) {
                GameFieldToy toy = gameFieldLogic.getToy();
                Image toyIcon = toy.getIcon().getImage();
                int toyX = toy.getX();
                int toyY = toy.getY();
                g.drawImage(toyIcon, toyX, toyY, this);
            }

            g.drawString("happiness: " + character.getHappiness(), 5, 15);
            g.drawString("fullness: " + character.getFullness(), 5, 30);
            g.drawString("age: " + character.getAge(), 5, 45);
        } else if(characterIsDead){
            g.drawImage(RIP.getImage(), ripX, ripY, this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameField)) return false;
        GameField gameField = (GameField) o;
        return characterIsDead == gameField.characterIsDead &&
                ripX == gameField.ripX &&
                ripY == gameField.ripY &&
                Objects.equals(gameFieldLogic, gameField.gameFieldLogic) &&
                Objects.equals(lifeTimer, gameField.lifeTimer) &&
                Objects.equals(ageTimer, gameField.ageTimer) &&
                Objects.equals(foodIconFileName, gameField.foodIconFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameFieldLogic, lifeTimer, ageTimer, characterIsDead, ripX, ripY, foodIconFileName);
    }

    public GameFieldLogicInterface getGameFieldLogic() {
        return gameFieldLogic;
    }
    public Timer getLifeTimer() {
        return lifeTimer;
    }

    public Timer getAgeTimer() {
        return ageTimer;
    }

    public int getRipX() {
        return ripX;
    }

    public int getRipY() {
        return ripY;
    }

    public String getFoodIconFileName() {
        return foodIconFileName;
    }
}
