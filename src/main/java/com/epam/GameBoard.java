package com.epam;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {

    public GameBoard() {
        setBackground(Color.pink);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel navigationPane = new JPanel();
        navigationPane.setBackground(Color.pink);
        navigationPane.setLayout(new BoxLayout(navigationPane, BoxLayout.PAGE_AXIS));

        navigationPane.add(getMainButtonsPane());
        navigationPane.add(Box.createRigidArea(new Dimension(0, 20)));
        navigationPane.add(getNavigationButtonsPane());

        add(getGameFieldPane());
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(navigationPane);

    }

    private JPanel getGameFieldPane() {
        JPanel gameFieldPane = new JPanel();
        gameFieldPane.setPreferredSize(new Dimension(280, 280));
        gameFieldPane.setBackground(Color.green);
        gameFieldPane.setBorder(BorderFactory.createLineBorder(Color.magenta));

        return gameFieldPane;
    }

    private JPanel getMainButtonsPane() {
        JPanel mainButtonsPane = new JPanel();
        mainButtonsPane.setBackground(Color.black);
        mainButtonsPane.setLayout(new BoxLayout(mainButtonsPane, BoxLayout.PAGE_AXIS));

        Button createBtn = new Button("create");
        createBtn.setBackground(Color.yellow);
        Button feedBtn = new Button("feed");
        feedBtn.setBackground(Color.yellow);

        mainButtonsPane.add(createBtn);
        mainButtonsPane.add(Box.createRigidArea(new Dimension(0, 10)));
        mainButtonsPane.add(feedBtn);

        return mainButtonsPane;
    }

    private JPanel getNavigationButtonsPane() {
        JPanel navigationButtonsPane = new JPanel();
        navigationButtonsPane.setBackground(Color.BLUE);
        navigationButtonsPane.setLayout(new BoxLayout(navigationButtonsPane, BoxLayout.PAGE_AXIS));

        JPanel upPane = new JPanel();
        upPane.setBackground(Color.cyan);

        Button upBtn = new Button("up");
        upBtn.setPreferredSize(new Dimension(40, 40));
        upBtn.setBackground(Color.yellow);
        upPane.add(upBtn);

        JPanel leftRightPane = new JPanel();
        leftRightPane.setBackground(Color.DARK_GRAY);
        leftRightPane.setLayout(new BoxLayout(leftRightPane, BoxLayout.LINE_AXIS));

        Button leftBtn = new Button("left");
        leftBtn.setPreferredSize(new Dimension(40, 40));
        leftBtn.setBackground(Color.yellow);
        Button rightBtn = new Button("right");
        rightBtn.setPreferredSize(new Dimension(40, 40));
        rightBtn.setBackground(Color.yellow);

        leftRightPane.add(leftBtn);
        leftRightPane.add(Box.createRigidArea(new Dimension(40, 0)));
        leftRightPane.add(rightBtn);

        JPanel downPane = new JPanel();
        downPane.setBackground(Color.LIGHT_GRAY);

        Button downBtn = new Button("down");
        downBtn.setPreferredSize(new Dimension(40, 40));
        downBtn.setBackground(Color.yellow);
        downPane.add(downBtn);

        navigationButtonsPane.add(upPane);
        navigationButtonsPane.add(leftRightPane);
        navigationButtonsPane.add(downPane);

        return navigationButtonsPane;
    }
}
