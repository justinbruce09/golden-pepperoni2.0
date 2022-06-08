package com.games.pizzaquest.textparser;

import java.util.ArrayList;
import java.util.Arrays;

public class TextParser {

    public void parse(String userInput) {
        //takes in user input and then splits it on the spaces. Logic comes later
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(userInput.toLowerCase().split(" ")));
        //print user input
        System.out.println(temp);
    }
}