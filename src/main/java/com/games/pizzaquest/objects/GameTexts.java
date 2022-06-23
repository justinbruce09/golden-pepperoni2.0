package com.games.pizzaquest.objects;

import com.games.pizzaquest.util.PizzaPrinter;

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
        PizzaPrinter.SOUT.println("You must use the following commands to navigate: ");
        PizzaPrinter.SOUT.println();
        for (GameText gt: texts) {
           gt.printHelp();
            PizzaPrinter.SOUT.println();
        }
    }
}
