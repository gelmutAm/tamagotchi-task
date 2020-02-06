package com.epam;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainWindow extends JFrame {

    GameBoard gameBoard;

    public MainWindow(GameBoard gameBoard) throws IOException, ClassNotFoundException {
        this.gameBoard = gameBoard;

        setTitle("Tamagotchi");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 320);
        setLocation(500, 200);
        add(this.gameBoard);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Date currentDate = new Date();
        File file = new File("gamefield.data");
        GameBoard gameBoard;

        if(file.exists()) {
            Serializator serializator = new Serializator();
            GameField gameField = serializator.deserialization("gamefield.data");
            int delay = gameField.getDelay();

            if(currentDate.getTime() - gameField.getCreationDate().getTime() > delay /** 5*/) {
                gameBoard = new GameBoard(new GameField());
            } else {
                long time = currentDate.getTime() - gameField.getCreationDate().getTime();
                long tickCount = time / delay;

                for(int i = 0; i < tickCount; i++) {
                    gameField.reduceIndicators();
                }

                gameBoard = new GameBoard(gameField);
            }
        } else {
            gameBoard = new GameBoard(new GameField());
        }

        MainWindow mainWindow = new MainWindow(gameBoard);
    }
}
