package com.games.pizzaquest.GUI;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.games.pizzaquest.util.PizzaPrinter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserInterfaceGUI implements ActionListener {
    private PizzaQuestApp pizzaQuestApp = new PizzaQuestApp();
    private static UserInterfaceGUI INSTANCE = new UserInterfaceGUI();

    public static UserInterfaceGUI getInstance(){
        return INSTANCE;
    }

//    public void gameRun(){
//        PizzaQuestApp app = new PizzaQuestApp();
//        PizzaQuestApp.setScanner(new Scanner(field.getText()));
//        app.execute();
//    }

    private boolean isNameSubmitted = false;
    JFrame frame;
    JTextField field;
    JPanel panel;


    private UserInterfaceGUI(){
        frame = new JFrame();
        field = new JTextField();
        JButton button = new JButton("Submit");
        button.addActionListener(this);
        panel = new JPanel();// sets panel to a new panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //sets the border of UI
        panel.setLayout(new GridLayout(3, 4, 1, 1));// gives rows and columns to UI
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2,1));
        panel2.add(field); //adds text field to panel
        panel2.add(button);//adds button to panel
        panel.setBounds(10,10,200,200);
//        panel.setSize(200, 200);



        for (PizzaPrinter printer : PizzaPrinter.values()) {// loops through the pizzaPrinter and assigns the vales to the corresponding labels
            if (printer != PizzaPrinter.SOUT) {

                JTextArea jTextArea = printer.getTarget();
                jTextArea.setFont(new Font("Dialog", Font.PLAIN,14));
                jTextArea.setWrapStyleWord(true);
                jTextArea.setLineWrap(true);
                jTextArea.setEditable(false);
                panel.add(jTextArea);
                printer.println();

            }

        }


        pizzaQuestApp.initialize();//begins program

        panel.add(panel2);

        //TODO: make decision on what do do with the map and it will go here
//        JPanel panel3 = new JPanel();
//        frame.add(panel3);


        //regular setup to GUI
        frame.add(panel, BorderLayout.CENTER);//adds panel to frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets behavior of closing panel
        frame.setTitle("PizzaQuest");//title of the window
        frame.pack();//sets window to match certain size
        frame.setVisible(true);//set the window to be visible and in focus
        frame.setSize(600, 500);




    }

    public static void main(String[] args){
     UserInterfaceGUI.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isNameSubmitted) {// if the user enters their name takes the text and begin turn logic
            pizzaQuestApp.turnLogic(field.getText());
        } else {
            pizzaQuestApp.welcomePrinter.println(pizzaQuestApp.enterName(field.getText()));
            isNameSubmitted = true;
        }
        if(pizzaQuestApp.END_OF_TURNS <= pizzaQuestApp.getTurns()){
            pizzaQuestApp.quitGame();
        }
        field.setText("");
    }

    public void refreshUI() {
//        frame
    }
}