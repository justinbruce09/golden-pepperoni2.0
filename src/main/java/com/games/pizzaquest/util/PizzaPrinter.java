package com.games.pizzaquest.util;

import javax.swing.*;

public enum PizzaPrinter {
    SOUT(null), LOCATION_TEXT_BOX(new JLabel()), INVENTORY(new JLabel()), QUEST_LOG(new JLabel());

    private JLabel target;
    private boolean newLine = true;

    JLabel getTarget(){
        return target;
    }

    void setTargetLocationAndSize(int x, int y, int height, int width){
        if(target != null) {
            target.setLocation(x, y);
            target.setSize(width, height);
        }
    }

    PizzaPrinter(JLabel target){
        this.target = target;
    }

    public void println() {
        println("");
    }

    public void println(String text){
        if (target == null) {
            System.out.println(text);
        } else {
            if (newLine) {
                target.setText(text);
            } else {
                target.setText(target.getText() + text);
            }
        }
        newLine = true;
    }


    public void print(String text){
        if (target == null) {
            System.out.print(text);
        } else {
            if (newLine) {
                target.setText(text);
            } else {
                target.setText(target.getText() + text);
            }
        }
        newLine = false;
    }
}
