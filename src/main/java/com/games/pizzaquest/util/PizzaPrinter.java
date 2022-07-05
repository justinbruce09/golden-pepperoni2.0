package com.games.pizzaquest.util;

import com.games.pizzaquest.GUI.UserInterfaceGUI;

import javax.swing.*;

public enum PizzaPrinter {
    SOUT(null),
    WELCOME(new JTextArea()),
    INVENTORY(new JTextArea()),
    QUEST_LOG(new JTextArea()),
    LOCATION(new JTextArea()),
    HELP(new JTextArea()),
    RESULT(new JTextArea()),
    REPUTATION(new JTextArea()),
    TURN(new JTextArea());

    private JTextArea target;
    private boolean newLine = true;
//    private static UserInterfaceGUI GUI = UserInterfaceGUI.getInstance();

    public JTextArea getTarget() {
        return target;
    }

    void setTargetLocationAndSize(int x, int y, int height, int width) {
        if (target != null) {
            target.setLocation(x, y);
            target.setSize(width, height);
        }
    }

    PizzaPrinter(JTextArea target) {
        this.target = target;
    }

    public void println() {
        println("");
    }

    public void println(String text) {
        if (target == null) {
            System.out.println(text);
        } else {
            if (newLine) {
                target.setText(name() + ": ");
            }
            target.append(text);
        }
        newLine = true;
    }


    public void print(String text) {
        println(text);
        newLine = false;
    }
}
