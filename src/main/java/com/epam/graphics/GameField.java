package com.epam.graphics;

import com.epam.logic.GameFieldLogic;
import com.epam.logic.GameFieldLogicInterface;
import com.epam.models.Age;
import com.epam.models.GameFieldCharacter;
import com.epam.models.GameFieldFood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GameField extends JPanel implements Serializable {
    private final int SIZE = 280;
    private final int CELL_SIZE = 35;
    private final int MIN_COORD = 0;
    private final int MAX_COORD = SIZE - CELL_SIZE;
    private final int TEMP_LIFE_DELAY = 60 * 1000 * 60;
    private final int TEMP_AGE_DELAY = TEMP_LIFE_DELAY * 10;
    private final int RIP_DELAY = 60 * 1000 / 2;
    private final ImageIcon RIP = new ImageIcon("src/main/resources/images/rip.png");

    private int LIFE_DELAY = TEMP_LIFE_DELAY;
    private int AGE_DELAY = TEMP_AGE_DELAY;

    private GameFieldLogicInterface gameFieldLogic;

    private Timer lifeTimer = new Timer(LIFE_DELAY, null);
    private Timer ageTimer = new Timer(AGE_DELAY, null);

    private boolean characterIsDead;
    private int ripX;
    private int ripY;
    private Date creationDate;
    private Date lastChanged;
    private int subtractedHappinessValue = 1;
    private int subtractedFullnessValue = 2;

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
        this.creationDate = gameField.getCreationDate();
        this.lastChanged = gameField.getLastChanged();
        this.foodIconFileName = gameField.getFoodIconFileName();
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

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public String getFoodIconFileName() {
        return foodIconFileName;
    }

    public void setFoodIconFileName(String fileName) {
        foodIconFileName = fileName;
    }

    public void startTimers(){
        lifeTimer.start();
        ageTimer.start();
    }

    public void setDelaysToStart() {
        lifeTimer.setInitialDelay(TEMP_LIFE_DELAY);
        lifeTimer.setDelay(TEMP_LIFE_DELAY);
        ageTimer.setInitialDelay(TEMP_AGE_DELAY);
        ageTimer.setDelay(TEMP_AGE_DELAY);
    }

    public void initLifeTimer() {
        lifeTimer.addActionListener((ActionEvent e) -> {
            GameFieldCharacter character = gameFieldLogic.getCharacter();

            if(character != null) {
                if (character.getFullness() > character.getFullnessMin() + subtractedFullnessValue) {
                    gameFieldLogic.reduceIndicators(subtractedHappinessValue, subtractedFullnessValue);
                    lastChanged = new Date();
                    LIFE_DELAY = TEMP_LIFE_DELAY;
                    lifeTimer.setInitialDelay(LIFE_DELAY);
                    lifeTimer.setDelay(LIFE_DELAY);
                    lifeTimer.restart();
                } else {
                    allToStart(character);
                    ageTimer.stop();
                    LIFE_DELAY = RIP_DELAY;
                    lifeTimer.setInitialDelay(LIFE_DELAY);
                    lifeTimer.setDelay(LIFE_DELAY);
                    lifeTimer.restart();
                }
            } else {
                characterIsDead = false;
                setDelaysToStart();
            }

            repaint();
        });
    }

    public void initAgeTimer() {
        ageTimer.addActionListener((ActionEvent e) -> {
            if(gameFieldLogic.getCharacter() != null) {
                if (gameFieldLogic.getCharacter().getAge() == Age.ELDERLY) {
                    allToStart(gameFieldLogic.getCharacter());
                    lifeTimer.stop();
                    AGE_DELAY = RIP_DELAY;
                    ageTimer.setInitialDelay(AGE_DELAY);
                    ageTimer.setDelay(AGE_DELAY);
                    ageTimer.restart();
                }

                if (gameFieldLogic.getCharacter() != null) {
                    gameFieldLogic.changeAge();
                    creationDate = new Date();
                    AGE_DELAY = TEMP_AGE_DELAY;
                    ageTimer.setInitialDelay(AGE_DELAY);
                    ageTimer.setDelay(AGE_DELAY);
                    ageTimer.restart();
                }
            } else {
                characterIsDead = false;
                setDelaysToStart();
            }

            repaint();
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
        creationDate = new Date();
        lastChanged = new Date();
        setDelaysToStart();
        lifeTimer.restart();
        ageTimer.restart();
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
            lastChanged = new Date();
            lifeTimer.setInitialDelay(TEMP_LIFE_DELAY);
            lifeTimer.setDelay(TEMP_LIFE_DELAY);
            lifeTimer.restart();
        }
    }

    private void allToStart(GameFieldCharacter character) {
        ripX = character.getX();
        ripY = character.getY();
        gameFieldLogic.setCharacterToNull();
        gameFieldLogic.setFoodToNull();
        characterIsDead = true;
    }

    public GameField recount(Date callDate) {
        if(gameFieldLogic.getCharacter() != null) {
            GameFieldCharacter character = gameFieldLogic.getCharacter();

            int maxLifeTicksCount = character.getFullness() / subtractedFullnessValue;
            int maxLifeDelay = maxLifeTicksCount * TEMP_LIFE_DELAY;
            long lifePassedTime = callDate.getTime() - lastChanged.getTime();
            long agePassedTime = callDate.getTime() - creationDate.getTime();

            if (lifePassedTime >= maxLifeDelay) {
                allToStart(character);
                lifeTimer.setInitialDelay(RIP_DELAY);
                lifeTimer.setDelay(RIP_DELAY);
                return this;
            } else {
                long ageTicksNumber = agePassedTime / TEMP_AGE_DELAY;

                if(ageTicksNumber >= 1 && character.getAge() == Age.ELDERLY) {
                    allToStart(character);
                    lifeTimer.setInitialDelay(RIP_DELAY);
                    lifeTimer.setDelay(RIP_DELAY);
                } else {
                    long lifeTicksNumber = lifePassedTime / TEMP_LIFE_DELAY;

                    for (int i = 0; i < lifeTicksNumber; i++) {
                        gameFieldLogic.reduceIndicators(subtractedHappinessValue, subtractedFullnessValue);
                    }

                    if(lifeTicksNumber == 0) {
                        LIFE_DELAY -= lifePassedTime;
                        lifeTimer.setInitialDelay(LIFE_DELAY);
                        lifeTimer.setDelay(LIFE_DELAY);
                        LIFE_DELAY = TEMP_LIFE_DELAY;
                    }

                    for(int i = 0; i < ageTicksNumber; i++) {
                        gameFieldLogic.changeAge();
                    }

                    if(ageTicksNumber == 0) {
                        AGE_DELAY -= agePassedTime;
                        ageTimer.setInitialDelay(AGE_DELAY);
                        ageTimer.setDelay(AGE_DELAY);
                        AGE_DELAY = TEMP_AGE_DELAY;
                    }
                }

                if (character.getFullness() == character.getFullnessMin()) {
                    allToStart(gameFieldLogic.getCharacter());
                    lifeTimer.setInitialDelay(RIP_DELAY);
                    lifeTimer.setDelay(RIP_DELAY);
                }

                return this;
            }
        }

        return this;
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
        return LIFE_DELAY == gameField.LIFE_DELAY &&
                AGE_DELAY == gameField.AGE_DELAY &&
                characterIsDead == gameField.characterIsDead &&
                ripX == gameField.ripX &&
                ripY == gameField.ripY &&
                subtractedHappinessValue == gameField.subtractedHappinessValue &&
                subtractedFullnessValue == gameField.subtractedFullnessValue &&
                Objects.equals(gameFieldLogic, gameField.gameFieldLogic) &&
                Objects.equals(lifeTimer, gameField.lifeTimer) &&
                Objects.equals(ageTimer, gameField.ageTimer) &&
                Objects.equals(creationDate, gameField.creationDate) &&
                Objects.equals(lastChanged, gameField.lastChanged) &&
                Objects.equals(foodIconFileName, gameField.foodIconFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                LIFE_DELAY,
                AGE_DELAY,
                gameFieldLogic,
                lifeTimer,
                ageTimer,
                characterIsDead,
                ripX,
                ripY,
                creationDate,
                lastChanged,
                subtractedHappinessValue,
                subtractedFullnessValue,
                foodIconFileName);
    }
}