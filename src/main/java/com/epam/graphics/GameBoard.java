package com.epam.graphics;

import com.epam.logic.GameFieldLogic;
import com.epam.serialization.Serializator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.IOException;

public class GameBoard extends JPanel implements ActionListener {
    private final int DELAY = 1000;

    private Button createBtn;
    private Button feedBtn;

    private Button upBtn;
    private Button leftBtn;
    private Button rightBtn;
    private Button downBtn;

    private GameField gameField;
    private GameField previousGameField;

    private CharSelectionDialog charSelectionDialog;

    private List<ImageIcon> petIcons;
    private String foodIconFileName;

    private String creationMessage = "You can't create a pet. Please wait.";

    private Serializator serializator = new Serializator();


    public GameBoard(GameField gameField) {
        setBackground(Color.pink);

        this.gameField = gameField;
        this.gameField.initLifeTimer();
        this.gameField.initAgeTimer();
        this.gameField.startTimers();

        if(this.gameField.characterExists()) {
            previousGameField = new GameField(this.gameField);
        } else {
            previousGameField = new GameField(new GameFieldLogic());
        }

        Timer timer = new Timer(DELAY, this);
        timer.start();

        JPanel navigationPane = new JPanel();
        navigationPane.setBackground(Color.pink);
        navigationPane.setLayout(new BoxLayout(navigationPane, BoxLayout.PAGE_AXIS));

        navigationPane.add(getMainButtonsPane());
        navigationPane.add(Box.createRigidArea(new Dimension(0, 20)));
        navigationPane.add(getNavigationButtonsPane());

        add(this.gameField, BorderLayout.CENTER);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(navigationPane);

        createBtn.addActionListener((ActionEvent e) -> {
            if(!this.gameField.characterExists() && !this.gameField.characterIsDead()) {
                charSelectionDialog = new CharSelectionDialog();
                petIcons = charSelectionDialog.getCharacterIcons();
                foodIconFileName = charSelectionDialog.getFoodIconFileName();

                this.gameField.setFoodIconFileName(foodIconFileName);

                if(petIcons != null) {
                    this.gameField.createCharacter(petIcons);
                    this.gameField.repaint();
                }
            }else if(this.gameField.characterIsDead()){
                new CreationDialog(creationMessage);
            }
        });

        feedBtn.addActionListener((ActionEvent e) -> {
            if(this.gameField.characterExists()) {
                if(!this.gameField.foodExists()) {
                    this.gameField.createFood(1, 2);
                    this.gameField.repaint();
                }
            }
        });

        upBtn.addActionListener((ActionEvent e) -> {
            if(this.gameField.characterExists()) {
                this.gameField.moveUp();
                this.gameField.feed();
                this.gameField.repaint();
            }
        });

        downBtn.addActionListener((ActionEvent e) -> {
            if(this.gameField.characterExists()) {
                this.gameField.moveDown();
                this.gameField.feed();
                this.gameField.repaint();
            }
        });

        leftBtn.addActionListener((ActionEvent e) -> {
            if(this.gameField.characterExists()) {
                this.gameField.moveLeft();
                this.gameField.feed();
                this.gameField.repaint();
            }
        });

        rightBtn.addActionListener((ActionEvent e) -> {
            if(this.gameField.characterExists()) {
                this.gameField.moveRight();
                this.gameField.feed();
                this.gameField.repaint();
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

        mainButtonsPane.add(createBtn);
        mainButtonsPane.add(Box.createRigidArea(new Dimension(0, 10)));
        mainButtonsPane.add(feedBtn);

        return mainButtonsPane;
    }

    private JPanel getNavigationButtonsPane() {
        JPanel navigationButtonsPane = new JPanel();
        navigationButtonsPane.setBackground(Color.pink);
        navigationButtonsPane.setLayout(new BoxLayout(navigationButtonsPane, BoxLayout.PAGE_AXIS));

        JPanel upPane = new JPanel();
        upPane.setBackground(Color.pink);

        upBtn = new Button("up");
        int NAV_BTN_SIZE = 40;
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
        if(!gameField.equals(previousGameField)) {
            try {
                serializator.serialization(gameField);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            previousGameField = new GameField(gameField);
        }
    }
}
