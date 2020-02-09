package com.epam.graphics;

import com.epam.logic.GameFieldLogicInterface;
import com.epam.models.GameFieldCharacter;
import com.epam.models.GameFieldFood;
import com.epam.models.GameFieldToy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Objects;

public class GameField extends JPanel
        implements Serializable, ActionListener {
    private final int SIZE = 280;
    private final int CELL_SIZE = 35;
    private final int MIN_COORD = 0;
    private final int MAX_COORD = SIZE - CELL_SIZE;
    private final int DELAY = 60000;
    private final ImageIcon RIP = new ImageIcon("rip.png");

    private GameFieldLogicInterface gameFieldLogic;

    private Timer timer = new Timer(DELAY, this);
    private int playCount = 0;
    private String creationMessage = "";
    private int ripX;
    private int ripY;

    public GameField(GameFieldLogicInterface gameFieldLogic) {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.green);
        setBorder(BorderFactory.createLineBorder(Color.magenta));

        this.gameFieldLogic = gameFieldLogic;
    }

    public GameField(GameField gameField) {
        this.gameFieldLogic = gameField.getGameFieldLogic();
        this.timer = gameField.getTimer();
        this.playCount = gameField.getPlayCount();
        this.creationMessage = gameField.getCreationMessage();
        this.ripX = gameField.getRipX();
        this.ripY = gameField.getRipY();
    }

    public void initTimer() {
        timer.start();
    }

    public void restartTimer() {
        timer.restart();
    }

    public void stopTimer() {
        timer.stop();
    }

    public boolean characterExists() {
        return gameFieldLogic.getCharacter() != null;
    }

    public void createCharacter(String iconFileName) {
        gameFieldLogic.createCharacter(iconFileName, CELL_SIZE, 3);
        initTimer();
    }

    public void createFood(String iconFileName, int increaseHappinessValue, int increaseFullnessValue) {
        gameFieldLogic.createFood(iconFileName,
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
            restartTimer();
        }
    }

    public void play(String iconFileName, int increaseHappinessValue) {
        stopTimer();
        gameFieldLogic.play(iconFileName, increaseHappinessValue, SIZE, CELL_SIZE);
        restartTimer();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(gameFieldLogic.getCharacter() != null) {
            GameFieldCharacter character = gameFieldLogic.getCharacter();
            Image charIcon = character.getIcon().getImage();
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
        } else if(playCount == 1){
            g.drawImage(RIP.getImage(), ripX, ripY, this);
            g.setColor(Color.red);
            g.drawString(creationMessage, CELL_SIZE * 2, SIZE / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameFieldCharacter character = gameFieldLogic.getCharacter();
        int happinessValue = 1;
        int fullnessValue = 2;

        if(character != null) {
            if (character.getFullness() > character.getFullnessMin() + fullnessValue) {
                gameFieldLogic.reduceIndicators(happinessValue, fullnessValue);
            } else {
                ripX = character.getX();
                ripY = character.getY();
                gameFieldLogic.setCharacterToNull();
                gameFieldLogic.setFoodToNull();
                playCount++;
            }
        } else {
            playCount = 0;
            creationMessage = "";
        }

        repaint();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameField)) return false;
        GameField gameField = (GameField) o;
        return SIZE == gameField.SIZE &&
                CELL_SIZE == gameField.CELL_SIZE &&
                MIN_COORD == gameField.MIN_COORD &&
                MAX_COORD == gameField.MAX_COORD &&
                DELAY == gameField.DELAY &&
                playCount == gameField.playCount &&
                ripX == gameField.ripX &&
                ripY == gameField.ripY &&
                Objects.equals(RIP, gameField.RIP) &&
                Objects.equals(gameFieldLogic, gameField.gameFieldLogic) &&
                Objects.equals(timer, gameField.timer) &&
                Objects.equals(creationMessage, gameField.creationMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(SIZE,
                CELL_SIZE,
                MIN_COORD,
                MAX_COORD,
                DELAY,
                RIP,
                gameFieldLogic,
                timer,
                playCount,
                creationMessage,
                ripX,
                ripY
        );
    }

    public GameFieldLogicInterface getGameFieldLogic() {
        return gameFieldLogic;
    }

    public Timer getTimer() {
        return timer;
    }

    public int getPlayCount() {
        return playCount;
    }

    public String getCreationMessage() {
        return creationMessage;
    }

    public int getRipX() {
        return ripX;
    }

    public int getRipY() {
        return ripY;
    }
}
