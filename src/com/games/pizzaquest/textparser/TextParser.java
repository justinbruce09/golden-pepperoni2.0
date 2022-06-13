package com.games.pizzaquest.textparser;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.games.pizzaquest.objects.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TextParser extends PizzaQuestApp {
    /*
    A class that is designed to take in user input and call other function depending on
    what the user types.
         */
    String currentInput="";
    public void parse(String userInput) {
        currentInput = userInput;
        //takes in user input and then splits it on the spaces. Logic comes later
        List<String> parsedUserInput = new ArrayList<>(Arrays.asList(userInput.toLowerCase().split(" ")));
        //after we break up the user input send it to be process
        processCommands(parsedUserInput);
    }

    //take the processed command and the delegates this to another
    private void processCommands(List<String> verbAndNounList){
        String noun = "";
        if(verbAndNounList.size()>1){
            noun = verbAndNounList.get(verbAndNounList.size()-1);
        }
        String verb = verbAndNounList.get(0);



        switch (verb) {
            case "quit":
                quitGame();
                break;
            case "go":
                if (noun.equals("")){
                    break;
                }
                //go(verbAndNounList); // send to method to process next part of command
                //we will need a valid location list to validate
//                                String loc = verbAndNounList.get(1);
                player.setLocation(noun);
                break;
            case "look":
                //look(); //player location or item  description printed
                //will need a item list and a location list
                //todo - check size and get last
                //if room, do the first, else if item, do the second
                if (noun.equals("")){
                    break;
                }
                if(itemList.contains(noun)){
                    System.out.println(player.look(new Item(noun)));
                }
                else{
                    System.out.println(player.look(player.getLocation()));
                }
                break;
            case "take":
                //add item to inventory
                player.addToInventory(noun);
                break;
            case "give":
                //removes item from inventory
                if (noun.equals("")){
                    break;
                }
                player.removeFromInventory(noun);
                break;
            case "inventory":

                Set<Item> tempInventory = player.getInventory();
                System.out.println("Items in the Inventory");
                for (Item item : tempInventory) {
                    System.out.println(item.getName());
                }
                break;
            case "help":
                gameInstructions();
                break;
            case "reset":
                resetGame();
                break;
            default:
                System.out.printf("I don't understand '%s'%n", currentInput);
                gameInstructions();
                break;
        }
    }





}