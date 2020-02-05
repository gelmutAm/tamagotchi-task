package com.epam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameField extends JPanel
        implements ActionListener {
    private final int SIZE = 280;
    private final int DOT_SIZE = 28;
    private final int DELAY = 60000;

    private String creationMessage = "";
    private int count = 0;

    private Timer timer = new Timer(DELAY, this);

    private Pet pet = new Pet();
    private Food food = new Food();

    private Integer previousFullnessValue;

    public GameField() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.green);
        setBorder(BorderFactory.createLineBorder(Color.magenta));
    }

    public boolean hasPetIcon() {
        return pet.icon != null;
    }

    public void setPetIcon(String fileName){
        if(timer.getDelay() == DELAY) {
            pet.icon = loadImage(fileName);
            pet.happiness = 1 /*Pet.HAPPINESS_MAX_VALUE*/;
            pet.fullness = 2 /*Pet.FULLNESS_MAX_VALUE*/;
            previousFullnessValue = pet.fullness;

            timer.start();
        } else {
            creationMessage = "You can't create a pet. Please wait";
        }
    }

    public void setFoodIcon(String fileName) {
        if(!pet.x.equals(food.x) || !pet.y.equals(food.x)) {
            food.icon = loadImage(fileName);
        }
    }

    private Image loadImage(String fileName) {
        ImageIcon iip = new ImageIcon(fileName);
        return iip.getImage();
    }

    public void moveUp() {
        if(pet.y >  0) {
            pet.y -= DOT_SIZE;
        }
    }

    public void moveDown() {
        if(pet.y <  SIZE - DOT_SIZE) {
            pet.y += DOT_SIZE;
        }
    }

    public void moveLeft() {
        if(pet.x > 0) {
            pet.x -= DOT_SIZE;
        }
    }

    public void moveRight() {
        if(pet.x < SIZE - DOT_SIZE) {
            pet.x += DOT_SIZE;
        }
    }

    public void feed() {
        if(pet.x.equals(food.x) && pet.y.equals(food.y)) {
            food.icon = null;

            if(pet.happiness < Pet.HAPPINESS_MAX_VALUE) {
                pet.happiness += 1;
            }
            pet.fullness = Pet.FULLNESS_MAX_VALUE;
            previousFullnessValue = pet.fullness;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pet.icon, pet.x, pet.y, this);
        g.drawImage(food.icon, food.x, food.y, this);
        g.setColor(Color.black);
        g.drawString("happiness: " + pet.happiness, 5, 15);
        g.drawString("fullness: " + pet.fullness, 5, 30);
        g.drawString(creationMessage, 0, SIZE / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(previousFullnessValue.equals(pet.fullness)) {
            if(pet.fullness > Pet.FULLNESS_MIN_VALUE + 2) {
                pet.fullness -= 2;
                previousFullnessValue = pet.fullness;
                pet.happiness--;
                timer.restart();
            } else if(count == 0) {
                pet.icon = null;
                pet.fullness = Pet.FULLNESS_MIN_VALUE;
                previousFullnessValue = pet.fullness;
                pet.happiness = Pet.HAPPINESS_MIN_VALUE;
                food.icon = null;
                count++;

                timer.setDelay(70000);
                timer.restart();
            } else if(count > 0) {
                timer.setDelay(DELAY);
                count = 0;
                creationMessage = "";
            }
        }
        repaint();
    }

    private class Pet {
        static final int HAPPINESS_MIN_VALUE = 0;
        static final int HAPPINESS_MAX_VALUE = 10;
        static final int FULLNESS_MIN_VALUE = 0;
        static final int FULLNESS_MAX_VALUE = 10;

        Integer x;
        Integer y;
        Integer happiness;
        Integer fullness;
        Image icon;

        Pet() {
            this.x = 0;
            this.y = 0;
            this.happiness = HAPPINESS_MIN_VALUE;
            this.fullness = FULLNESS_MIN_VALUE;
        }
    }

    private class Food {
        Integer x;
        Integer y;
        Image icon;

        Food() {
            this.x = SIZE - DOT_SIZE;
            this.y = SIZE - DOT_SIZE;
        }
    }
}
