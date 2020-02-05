package com.epam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameBoard extends JPanel {
    private final int NAV_BTN_SIZE = 40;

    private Button createBtn;
    private Button feedBtn;

    private Button upBtn;
    private Button leftBtn;
    private Button rightBtn;
    private Button downBtn;

    private GameField gameField;
    private JPanel navigationPane;

    private String petIconFileName = "cat3.png";
    private String foodIconFileName = "fish-fish.png";

    public GameBoard() {
        setBackground(Color.pink);

        gameField = new GameField();

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
            if(!gameField.hasPetIcon()) {
                gameField.setPetIcon(petIconFileName);
                repaint();
            }
        });

        feedBtn.addActionListener((ActionEvent e) -> {
            if(gameField.hasPetIcon()) {
                gameField.setFoodIcon(foodIconFileName);
                gameField.repaint();
            }
        });

        upBtn.addActionListener((ActionEvent e) -> {
            gameField.moveUp();
            gameField.feed();
            gameField.repaint();
        });

        downBtn.addActionListener((ActionEvent e) -> {
            gameField.moveDown();
            gameField.feed();
            gameField.repaint();
        });

        leftBtn.addActionListener((ActionEvent e) -> {
            gameField.moveLeft();
            gameField.feed();
            gameField.repaint();
        });

        rightBtn.addActionListener((ActionEvent e) -> {
            gameField.moveRight();
            gameField.feed();
            gameField.repaint();
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
}