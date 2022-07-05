package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;

public class GameText {
    private String command;
    private String option;
    private String description;


    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void printHelp() {
        String help = "\"" + command + translateOption() + "\" " + description + ".";
        PizzaQuestApp.helpPrinter.print(help);


    }

    private String translateOption() {
        if (option.isEmpty()) {
            return "";
        }
        return " [" + option + "]";
    }
}
