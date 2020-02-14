package com.epam.graphics;

import com.epam.logic.GameFieldLogic;
import com.epam.serialization.Serializator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainWindow extends JFrame {

    GameBoard gameBoard;

    public MainWindow(GameBoard gameBoard) {
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
        Serializator serializator = new Serializator();
        File file = new File("gamefield.data");
        GameBoard gameBoard;

        if(file.exists()) {
            GameField gameField = serializator.deserialization("gamefield.data");
            gameBoard = new GameBoard(gameField.recount(new Date()));
        } else {
            gameBoard = new GameBoard(new GameField(new GameFieldLogic()));
        }

        MainWindow mainWindow = new MainWindow(gameBoard);
    }
}
