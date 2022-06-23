package com.games.pizzaquest.objects;

import java.util.ArrayList;

public class GameTexts {
    ArrayList<GameText> texts;

    public ArrayList<GameText> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<GameText> texts) {
        this.texts = texts;
    }

    // Create a method that will print all the GameText in the ArrayList
    public void displayCommands() {
        System.out.println("You must use the following commands to navigate: ");
        System.out.println();
        for (GameText gt: texts) {
           gt.printHelp();
            System.out.println();
        }
    }
}
