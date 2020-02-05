package com.epam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class GameField extends JPanel
        implements ActionListener, Serializable {
    private final int SIZE = 280;
    private final int DOT_SIZE = 28;
    private final int DELAY = 60000;
    private final int TIME_TO_WAIT = 70000;

    private String creationMessage = "";

    private Timer timer = new Timer(DELAY, this);

    private Pet pet;
    private Food food;

    public GameField() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.green);
        setBorder(BorderFactory.createLineBorder(Color.magenta));
    }

    public boolean hasPet() {
        return pet != null;
    }

    public void createPet(String fileName){
        if(timer.getDelay() == DELAY) {
            pet = new Pet();
            pet.icon = loadImage(fileName);
            pet.happiness = 1;
            pet.fullness = 2;
            timer.start();
        } else {
            creationMessage = "You can't create a pet. Please wait.";
        }
    }

    public void createFood(String fileName) {
        if(pet != null) {
            food = new Food();

            if (!pet.x.equals(food.x) || !pet.y.equals(food.y)) {
                food.icon = loadImage(fileName);
            }
        }
    }

    private Image loadImage(String fileName) {
        ImageIcon iip = new ImageIcon(fileName);
        return iip.getImage();
    }

    public void moveUp() {
        if(pet != null) {
            if (pet.y > 0) {
                pet.y -= DOT_SIZE;
            }
        }
    }

    public void moveDown() {
        if(pet != null) {
            if (pet.y < SIZE - DOT_SIZE) {
                pet.y += DOT_SIZE;
            }
        }
    }

    public void moveLeft() {
        if(pet != null) {
            if (pet.x > 0) {
                pet.x -= DOT_SIZE;
            }
        }
    }

    public void moveRight() {
        if(pet != null) {
            if (pet.x < SIZE - DOT_SIZE) {
                pet.x += DOT_SIZE;
            }
        }
    }

    public void feed() {
        if(food != null) {
            if (pet.x.equals(food.x) && pet.y.equals(food.y)) {
                food = null;

                if (pet.happiness < Pet.HAPPINESS_MAX_VALUE) {
                    pet.happiness += 1;
                }

                pet.fullness = Pet.FULLNESS_MAX_VALUE;
            }
        }
    }

    public void reduceIndicators() {
        pet.fullness -= 2;
        pet.happiness--;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(pet != null) {
            g.drawImage(pet.icon, pet.x, pet.y, this);

            if(food != null) {
                g.drawImage(food.icon, food.x, food.y, this);
            }

            g.setColor(Color.black);
            g.drawString("happiness: " + pet.happiness, 5, 15);
            g.drawString("fullness: " + pet.fullness, 5, 30);
        }

        g.drawString(creationMessage, DOT_SIZE * 2, SIZE / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(pet != null) {
            if(pet.fullness > Pet.FULLNESS_MIN_VALUE + 2) {
                reduceIndicators();
                timer.restart();
            } else {
                pet = null;
                food = null;
                timer.setDelay(TIME_TO_WAIT);
                timer.restart();
            }
        } else {
            timer.setDelay(DELAY);
            creationMessage = "";
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
            this.happiness = HAPPINESS_MAX_VALUE;
            this.fullness = FULLNESS_MAX_VALUE;
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
