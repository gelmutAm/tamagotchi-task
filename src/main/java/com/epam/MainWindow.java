package com.epam;

import javax.swing.*;
import java.io.IOException;

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
        /*Serializator serializator = new Serializator();
        GameField gameField = serializator.deserialization("gamefield.data");
//        gameField.print();
        GameBoard gameBoard = new GameBoard(gameField);*/

        GameBoard gameBoard = new GameBoard(new GameField());

        MainWindow mainWindow = new MainWindow(gameBoard);

        /*Serializator serializator = new Serializator();
        GameField gameField = serializator.deserialization("gamefield.data");
        gameField.print();*/
    }
}
