package com.epam;

import java.io.*;

public class Serializator implements Serializable{

    public boolean serialization(GameField gameField) throws IOException {
        File file = new File("gamefield.data");

        FileOutputStream fos = new FileOutputStream(file);
        if(fos != null) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameField);
            oos.close();

            return true;
        }

        return false;
    }

    public GameField deserialization(String fileName) throws IOException, ClassNotFoundException {
        GameField gameField = null;
        File file = new File(fileName);

        FileInputStream fis = new FileInputStream(file);
        if(fis != null) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            gameField = (GameField) ois.readObject();
            ois.close();
        }

        return gameField;
    }
}
