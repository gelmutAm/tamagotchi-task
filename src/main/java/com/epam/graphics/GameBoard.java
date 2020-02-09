package com.epam.graphics;

import com.epam.serialization.Serializator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameBoard extends JPanel implements ActionListener {
    private final int NAV_BTN_SIZE = 40;
    private final int DELAY = 1000;

    private Button createBtn;
    private Button feedBtn;
    private Button playBtn;

    private Button upBtn;
    private Button leftBtn;
    private Button rightBtn;
    private Button downBtn;

    private GameField gameField;
    //private GameField previousGameField;
    private JPanel navigationPane;
    private CharSelectionDialog charSelectionDialog;

    private String petIconFileName;
    private String foodIconFileName;
    private String toyIconFileName;

    private String creationMessage = "You can't create a pet. Please wait.";

    Serializator serializator = new Serializator();
    Timer timer = new Timer(DELAY, this);


    public GameBoard(GameField gameField) {
        setBackground(Color.pink);

        this.gameField = gameField;
        this.gameField.initTimer();
        //gameField = new GameField(new GameFieldLogic());
        //previousGameField = new GameField(gameField);

        timer.start();

        JPanel navigationPane = new JPanel();
        navigationPane.setBackground(Color.pink);
        navigationPane.setLayout(new BoxLayout(navigationPane, BoxLayout.PAGE_AXIS));

        navigationPane.add(getMainButtonsPane());
        navigationPane.add(Box.createRigidArea(new Dimension(0, 20)));
        navigationPane.add(getNavigationButtonsPane());

        this.navigationPane = navigationPane;

        add(gameField, BorderLayout.CENTER);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(this.navigationPane);

        createBtn.addActionListener((ActionEvent e) -> {
            if(!gameField.characterExists() && gameField.getPlayCount() != 1) {
                charSelectionDialog = new CharSelectionDialog();
                petIconFileName = charSelectionDialog.getCharacterIconFileName();
                foodIconFileName = charSelectionDialog.getFoodIconFileName();
                toyIconFileName = charSelectionDialog.getToyIconFileName();

                if(petIconFileName != null) {
                    gameField.createCharacter(charSelectionDialog.getCharacterIconFileName());
                    gameField.repaint();
                }
            }else if(gameField.getPlayCount() == 1){
                new CreationDialog(creationMessage);
            }
        });

        feedBtn.addActionListener((ActionEvent e) -> {
            if(gameField.characterExists()) {
                gameField.createFood(charSelectionDialog.getFoodIconFileName(), 1, 2);
                gameField.repaint();
            }
        });

        playBtn.addActionListener((ActionEvent e) -> {
            /*for(int i = 0; i < 5; i++) {
                gameField.play(charSelectionDialog.getToyIconFileName(), 2);
                gameField.repaint();
            }*/
        });

        upBtn.addActionListener((ActionEvent e) -> {
            if(gameField.characterExists()) {
                gameField.moveUp();
                gameField.feed();
                gameField.repaint();
            }
        });

        downBtn.addActionListener((ActionEvent e) -> {
            if(gameField.characterExists()) {
                gameField.moveDown();
                gameField.feed();
                gameField.repaint();
            }
        });

        leftBtn.addActionListener((ActionEvent e) -> {
            if(gameField.characterExists()) {
                gameField.moveLeft();
                gameField.feed();
                gameField.repaint();
            }
        });

        rightBtn.addActionListener((ActionEvent e) -> {
            if(gameField.characterExists()) {
                gameField.moveRight();
                gameField.feed();
                gameField.repaint();
            }
        });
    }

    private JPanel getMainButtonsPane() {
        JPanel mainButtonsPane = new JPanel();
        mainButtonsPane.setBackground(Color.pink);
        mainButtonsPane.setLayout(new BoxLayout(mainButtonsPane, BoxLayout.PAGE_AXIS));

        createBtn = new Button("create");
        createBtn.setBackground(Color.yellow);
        feedBtn = new Button("feed");
        feedBtn.setBackground(Color.yellow);
        playBtn = new Button("play");
        playBtn.setBackground(Color.yellow);

        mainButtonsPane.add(createBtn);
        mainButtonsPane.add(Box.createRigidArea(new Dimension(0, 10)));
        mainButtonsPane.add(feedBtn);
        mainButtonsPane.add(Box.createRigidArea(new Dimension(0, 10)));
        mainButtonsPane.add(playBtn);

        return mainButtonsPane;
    }

    private JPanel getNavigationButtonsPane() {
        JPanel navigationButtonsPane = new JPanel();
        navigationButtonsPane.setBackground(Color.pink);
        navigationButtonsPane.setLayout(new BoxLayout(navigationButtonsPane, BoxLayout.PAGE_AXIS));

        JPanel upPane = new JPanel();
        upPane.setBackground(Color.pink);

        upBtn = new Button("up");
        upBtn.setPreferredSize(new Dimension(NAV_BTN_SIZE, NAV_BTN_SIZE));
        upBtn.setBackground(Color.yellow);
        upPane.add(upBtn);

        JPanel leftRightPane = new JPanel();
        leftRightPane.setBackground(Color.pink);
        leftRightPane.setLayout(new BoxLayout(leftRightPane, BoxLayout.LINE_AXIS));

        leftBtn = new Button("left");
        leftBtn.setPreferredSize(new Dimension(NAV_BTN_SIZE, NAV_BTN_SIZE));
        leftBtn.setBackground(Color.yellow);
        rightBtn = new Button("right");
        rightBtn.setPreferredSize(new Dimension(NAV_BTN_SIZE, NAV_BTN_SIZE));
        rightBtn.setBackground(Color.yellow);

        leftRightPane.add(leftBtn);
        leftRightPane.add(Box.createRigidArea(new Dimension(NAV_BTN_SIZE, 0)));
        leftRightPane.add(rightBtn);

        JPanel downPane = new JPanel();
        downPane.setBackground(Color.pink);

        downBtn = new Button("down");
        downBtn.setPreferredSize(new Dimension(NAV_BTN_SIZE, NAV_BTN_SIZE));
        downBtn.setBackground(Color.yellow);
        downPane.add(downBtn);

        navigationButtonsPane.add(upPane);
        navigationButtonsPane.add(leftRightPane);
        navigationButtonsPane.add(downPane);

        return navigationButtonsPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*try {
            serializator.serialization(gameField);
        } catch (IOException e1) {
            e1.printStackTrace();
        }*/
    }
}
