package com.epam;

import com.epam.models.Pet;

import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {
    private final int SIZE = 280;
    private final int DOT_SIZE = 28;
    private final int ALL_DOTS = 100;


    private Pet pet = new Pet();

    public GameField() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.green);
        setBorder(BorderFactory.createLineBorder(Color.magenta));
    }

    public GameField(String fileName) {
        this();
        pet.setPicture(loadImage(fileName));
        createPet();
    }

    private Image loadImage(String fileName) {
        ImageIcon iip = new ImageIcon(fileName);
        return iip.getImage();
    }

    private void createPet() {
        pet.setxCoord((int) Math.sqrt((double) ALL_DOTS) / 2 * DOT_SIZE);
        pet.setyCoord((int) Math.sqrt((double) ALL_DOTS) / 2 * DOT_SIZE);
    }

    public void moveUp() {
        if(pet.getyCoord() >  0) {
            pet.setyCoord(pet.getyCoord() - DOT_SIZE);
        }
    }

    public void moveDown() {
        if(pet.getyCoord() < SIZE - DOT_SIZE){
            pet.setyCoord(pet.getyCoord() + DOT_SIZE);
        }
    }

    public void moveLeft() {
        if(pet.getxCoord() > 0) {
            pet.setxCoord(pet.getxCoord() - DOT_SIZE);
        }
    }

    public void moveRight() {
        if(pet.getxCoord() < SIZE - DOT_SIZE) {
            pet.setxCoord(pet.getxCoord() + DOT_SIZE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pet.getPicture(), pet.getxCoord(), pet.getyCoord(), this);
    }
}
