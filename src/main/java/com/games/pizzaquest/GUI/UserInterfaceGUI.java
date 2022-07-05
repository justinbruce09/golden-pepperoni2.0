package com.games.pizzaquest.GUI;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.games.pizzaquest.util.PizzaPrinter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;


public class UserInterfaceGUI implements ActionListener {
    private PizzaQuestApp pizzaQuestApp = new PizzaQuestApp();
    private static UserInterfaceGUI INSTANCE = new UserInterfaceGUI();

    public static UserInterfaceGUI getInstance(){
        return INSTANCE;
    }


    private boolean isNameSubmitted = false;
    JFrame frame;
    JTextField field;
    JPanel panel;


    private UserInterfaceGUI(){
        frame = new JFrame();
        field = new JTextField();
        JButton button = new JButton("Submit");
        button.addActionListener(this);
        //main panel for display
        panel = new JPanel();// sets panel to a new panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //map panel
        Path filePath = Path.of("./src/main/resources/pizza_quest_map.png");
        System.out.println(filePath.toAbsolutePath());

        JPanel mapPanel = new JPanel();
        try{
            ImageIcon mapImage = new ImageIcon(filePath.toUri().toURL());
            JLabel mapLabel = new JLabel(mapImage);
            mapPanel.add(mapLabel);
            mapLabel.setVisible(true);
        }catch(Exception e){}


        //sets the border of UI
        panel.setLayout(new GridLayout(1, 3, 1, 1));// gives rows and columns to UI
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2,1));
        panel2.add(field); //adds text field to panel
        panel2.add(button);//adds button to panel

        // layout Panel
        JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new GridLayout(1, 2, 1,1));
        layoutPanel.add(panel);
        layoutPanel.add(mapPanel);
        frame.add(layoutPanel);


        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new GridLayout(3, 1, 1, 1));
        panel.add(leftColumn);
        initilizeTextField(PizzaPrinter.WELCOME, leftColumn);
        initilizeTextField(PizzaPrinter.INVENTORY, leftColumn);
        initilizeTextField(PizzaPrinter.QUEST_LOG, leftColumn);

        JPanel centerColumn = new JPanel();
        centerColumn.setLayout(new GridLayout(2, 1, 1, 1));
        panel.add(centerColumn);
        initilizeTextField(PizzaPrinter.LOCATION, centerColumn);
        initilizeTextField(PizzaPrinter.HELP, centerColumn);

        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new GridLayout(4, 1, 1, 1));
        panel.add(rightColumn);
        initilizeTextField(PizzaPrinter.REPUTATION, rightColumn);
        initilizeTextField(PizzaPrinter.TURN, rightColumn);
        initilizeTextField(PizzaPrinter.RESULT, rightColumn);
        rightColumn.add(panel2);



        pizzaQuestApp.initialize();//begins program


        //regular setup to GUI
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets behavior of closing panel
        frame.setTitle("PizzaQuest");//title of the window
        frame.pack();//sets window to match certain size
        frame.setVisible(true);//set the window to be visible and in focus
        frame.setSize(1200, 750);


        pizzaQuestApp.gameInstructions();

    }

    private void initilizeTextField(PizzaPrinter printer, JPanel panel) {
        JTextArea jTextArea = printer.getTarget();
        jTextArea.setFont(new Font("Dialog", Font.PLAIN,14));
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);
        panel.add(jTextArea);
        printer.println();
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
}