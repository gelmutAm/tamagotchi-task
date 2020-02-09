package com.epam.graphics;

import javax.swing.*;
import java.awt.*;

public class CreationDialog extends JDialog {

    public CreationDialog(String message) {
        setModal(true);
        setTitle("Message");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(400, 200));
        setLocation(500, 200);
        add(getPane(message));

        setVisible(true);
        setResizable(false);

    }

    public JPanel getPane(String message) {
        JPanel pane = new JPanel();
        pane.setBackground(Color.pink);
        pane.setLayout(new BorderLayout());

        Font font = new Font("Verdana", Font.PLAIN, 12);

        JLabel label = new JLabel(message);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);

        pane.add(label);

        return pane;
    }
}
