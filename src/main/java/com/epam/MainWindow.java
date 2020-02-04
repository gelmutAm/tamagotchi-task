package com.epam;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Tamagotchi");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 320);
        setLocation(500, 200);
        add(new GameBoard());
        setVisible(true);
        //setResizable(false);
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
