package com.games.pizzaquest.textparser;

import com.games.pizzaquest.objects.Item;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class TextParser {
Gson pizzaGson = new Gson();

    public String parse(String userInput) {
        //takes in user input and then splits it on the spaces. Logic comes later
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(userInput.toLowerCase().split(" ")));
        //print user input
        System.out.println(temp);

        if (temp.get(0) == "quit" || temp.get(0) == "q" || temp.get(0) == "exit") {
            System.out.println("You'll always have a pizza our heart ... Goodbye!");

        }
        return userInput;
    }

    public void testJson() {
        Item item = new Item("coin");
        String json = pizzaGson.toJson(item);
        System.out.println(json);
        // converting json string back to an item object
        Item item1 = pizzaGson.fromJson(json, Item.class);
        System.out.println(item1);

    }

    public static void main(String[] args) {
        TextParser tp = new TextParser();
        tp.testJson();
    }

}