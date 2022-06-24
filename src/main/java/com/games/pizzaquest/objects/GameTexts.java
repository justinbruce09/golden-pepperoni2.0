package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;

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
        PizzaQuestApp.helpPrinter.print("You must use the following commands to navigate: \n\n");
        for (GameText gt: texts) {
           gt.printHelp();
            PizzaQuestApp.helpPrinter.print("\n");
        }
    }
}
