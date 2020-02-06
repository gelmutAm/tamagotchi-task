package com.epam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class GameField extends JPanel
        implements ActionListener, Serializable {
    private final int SIZE = 280;
    private final int DOT_SIZE = 28;
    private final int DELAY = 60000;
    private final int TIME_TO_WAIT = 70000;
    private final ImageIcon RIP_ICON = new ImageIcon("RIP.png");

    private int ripX;
    private int ripY;

    private int playCount = 0;
    private String creationMessage = "";
    private Date creationDate;
    private Timer timer = new Timer(DELAY, this);

    private Pet pet;
    private Food food;

    Serializator serializator = new Serializator();

    public GameField() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.green);
        setBorder(BorderFactory.createLineBorder(Color.magenta));
    }

    public void initTimer() {
        timer.start();
    }

    public boolean hasPet() {
        return pet != null;
    }

    public void createPet(String fileName){
        if(timer.getDelay() == DELAY) {
            pet = new Pet();
            pet.icon = new ImageIcon(fileName);
            pet.happiness = 1;
            pet.fullness = 2;
            creationDate = new Date();
            initTimer();
        } else {
            creationMessage = "You can't create a pet. Please wait.";
        }
    }

    public void createFood(String fileName) {
        if(pet != null) {
            food = new Food();

            if (!pet.x.equals(food.x) || !pet.y.equals(food.y)) {
                food.icon = new ImageIcon(fileName);
            } else {
                food = null;
            }
        }
    }

    private Image loadImage(String fileName) {
        ImageIcon ii = new ImageIcon(fileName);
        return ii.getImage();
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

        if(pet.happiness > Pet.HAPPINESS_MIN_VALUE) {
            pet.happiness--;
        }
    }

    public int getDelay() {
        return DELAY;
    }

    public int getCurrentDelay() {
        return timer.getDelay();
    }

    public int getTimeToWait() {
        return TIME_TO_WAIT;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setHappiness(int happiness) {
        if(pet != null) {
            pet.happiness = happiness;
        }
    }

    public void setFullness(int fullness) {
        if(pet != null) {
            pet.fullness = fullness;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(pet != null) {
            g.drawImage(pet.icon.getImage(), pet.x, pet.y, this);

            if(food != null) {
                g.drawImage(food.icon.getImage(), food.x, food.y, this);
            }

            g.setColor(Color.black);
            g.drawString("happiness: " + pet.happiness, 5, 15);
            g.drawString("fullness: " + pet.fullness, 5, 30);
        } else if(playCount == 1) {
            g.drawImage(RIP_ICON.getImage(), ripX, ripY, this);
        }

        g.setColor(Color.RED);
        g.drawString(creationMessage, DOT_SIZE * 2, SIZE / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(pet != null) {
            if(pet.fullness > Pet.FULLNESS_MIN_VALUE + 2) {
                reduceIndicators();
                try {
                    serializator.serialization(this);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                timer.restart();
            } else {
                pet.happiness = 0;
                pet.fullness = 0;
                ripX = pet.x;
                ripY = pet.y;
                pet = null;
                food = null;
                playCount++;
                timer.setDelay(TIME_TO_WAIT);
                try {
                    serializator.serialization(this);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                timer.restart();
            }
        } else {
            playCount = 0;
            creationMessage = "";
            timer.setDelay(DELAY);
            try {
                serializator.serialization(this);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        repaint();
    }

    private class Pet implements Serializable{
        static final int HAPPINESS_MIN_VALUE = 0;
        static final int HAPPINESS_MAX_VALUE = 10;
        static final int FULLNESS_MIN_VALUE = 0;
        static final int FULLNESS_MAX_VALUE = 10;

        Integer x;
        Integer y;
        Integer happiness;
        Integer fullness;
        ImageIcon icon;

        Pet() {
            this.x = 3 * DOT_SIZE;
            this.y = 3 * DOT_SIZE;
            this.happiness = HAPPINESS_MAX_VALUE;
            this.fullness = FULLNESS_MAX_VALUE;
        }
    }

    private class Food implements Serializable{
        Integer x;
        Integer y;
        ImageIcon icon;

        Food() {
            this.x = SIZE - DOT_SIZE;
            this.y = SIZE - DOT_SIZE;
        }
    }
}
