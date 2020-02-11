package com.epam.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CharSelectionDialog extends JDialog {
    private List<ImageIcon> characterIcons;
    private String foodIconFileName;
    private String toyIconFileName;

    private Button dogBtn;
    private Button catBtn;

    public CharSelectionDialog() {
        setModal(true);
        setTitle("Select a pet");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(400, 200));
        setLocation(500, 200);
        add(getSelectionPane());

        dogBtn.addActionListener((ActionEvent e) -> {
            List<String> list = new ArrayList<>();
            list.add("dog_teen.png");
            list.add("dog_adult.png");
            list.add("dog_elderly.png");

            characterIcons = getIcons(list);
            foodIconFileName = "bone.png";
            toyIconFileName = "ball.png";
            this.dispose();
        });

        catBtn.addActionListener((ActionEvent e) -> {
            List<String> list = new ArrayList<>();
            list.add("cat_teen.png");
            list.add("cat_adult.png");
            list.add("cat_elderly.png");

            characterIcons = getIcons(list);
            foodIconFileName = "chicken.png";
            toyIconFileName = "bow.png";
            this.dispose();
        });

        setVisible(true);
        setResizable(false);
    }

    public JPanel getSelectionPane() {
        JPanel selectionPane = new JPanel();
        selectionPane.setBackground(Color.pink);
        selectionPane.setLayout(new BorderLayout());

        JPanel buttonsPane = new JPanel();
        buttonsPane.setBackground(Color.pink);
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));

        dogBtn = new Button("Dog");
        dogBtn.setBackground(Color.yellow);
        catBtn = new Button("Cat");
        catBtn.setBackground(Color.yellow);

        buttonsPane.add(dogBtn);
        buttonsPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonsPane.add(catBtn);

        selectionPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        selectionPane.add(buttonsPane, BorderLayout.CENTER);

        return selectionPane;
    }

    public List<ImageIcon> getCharacterIcons() {
        return characterIcons;
    }

    public String getFoodIconFileName() {
        return foodIconFileName;
    }

    public String getToyIconFileName() {
        return toyIconFileName;
    }

    private List<ImageIcon> getIcons(List<String> fileNames) {
        List<ImageIcon> result = new ArrayList<>();

        for(String item : fileNames) {
            result.add(new ImageIcon(item));
        }

        return result;
    }
}
