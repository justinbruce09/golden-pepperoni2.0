package com.games.pizzaquest.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUI implements ActionListener {

    private int count = 0;
    private String inputText;
    private JLabel label; // adds a label to panel
    private JFrame frame; //creates a new GUI
    private JPanel panel; //creates a new UI
    private JTextField field; // creates a new text field
    //GUI constructor
    public void GUI(){
        frame = new JFrame();// sets frame to a new frame
        field = new JTextField("Enter command here", 30); //sets field to a new field and the size of it and default text
        JButton button = new JButton("Submit");//creates a button in panel
        button.addActionListener(this);//uses an action listener method for this class
        //label = new JLabel("clicks"); sets label to clicks
        label = new JLabel();

        panel = new JPanel();// sets panel to a new panel
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        //sets the border of UI
        panel.setLayout(new GridLayout(0, 1));// gives rows and columns to UI
        panel.add(field); //adds text field to panel
        panel.add(button);//adds button to panel
        panel.add(label);//adds and field button to panel


        //regular setup to GUI
        frame.add(panel, BorderLayout.CENTER);//adds panel to frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets behavior of closing panel
        frame.setTitle("Practice GUI");//title of the window
        frame.pack();//sets window to match certain size
        frame.setVisible(true);//set the window to be visible and in focus
    }

    public static void main(String[] args){
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {// need to add method for action listener
        // put in here what to happen after button is clicked
        //count++; increment count
        //label.setText("clicks" + count); sets the label to the count going up
        inputText = field.getText();// gets the text from the input and turns it into a string
        label.setText(inputText); //sets the label text to the input text
    }
}