package com.epam;

import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {
    private final int SIZE = 280;
    private final int DOT_SIZE = 28;

    private Pet pet = new Pet();
    private Food food = new Food();

    public GameField() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.green);
        setBorder(BorderFactory.createLineBorder(Color.magenta));
    }

    public GameField(String fileName) {
        this();
        pet.icon = loadImage(fileName);
    }

    public boolean hasPetIcon() {
        return pet.icon != null;
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
        food.icon = loadImage("fish-fish.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pet.icon, pet.x, pet.y, this);
        g.drawImage(food.icon, food.x, food.y, this);
    }

    private class Pet {
        Integer x;
        Integer y;
        Integer happiness;
        Integer fullness;
        Image icon;

        Pet() {
            this.y = 0;
            this.x = 0;
            this.happiness = 10;
            this.fullness = 10;
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
