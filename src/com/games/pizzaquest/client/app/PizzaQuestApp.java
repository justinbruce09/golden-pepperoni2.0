package com.games.pizzaquest.client.app;
import com.games.pizzaquest.objects.Item;
import com.games.pizzaquest.objects.Location;
import com.games.pizzaquest.objects.Player;
import com.games.pizzaquest.textparser.TextParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PizzaQuestApp {

        //scanner for the game
        static Scanner scanner = new Scanner(System.in);
        //text parser for users to use
        //path for some ascii art
        private static final String bannerFilePath = "data/WelcomeSplash.txt";
        //track turn may be moved to player
        private int turns = 0;
        static final int END_OF_TURNS=1;

        public boolean isGameOver() {
                return isGameOver;
        }

        public void setGameOver(boolean gameOver) {
                isGameOver = gameOver;
        }
        private final List<String> itemList = List.of("Pizza Cutter", "Prosciutto", "Wine Glass", "Lemons", "Coin", "Ancient Pizza Cookbook", "Moped", "Cannoli", "Marble Sculpture", "Espresso");

        //Initial State of the Player, inventory and starting location
        private final Set<Item> inventory = new HashSet<>();
        private final Location location =  new Location("Naples");
        private final Player player = new Player(inventory, location);

        //keep the game running until win/lose condition is met
        private boolean isGameOver = false;
        private String userInput;


        public void execute() {
                //temporaily put in a 1 iteration loop to test user input
                while(turns < END_OF_TURNS) {
                        welcome();
                        gameInstructions();
                        System.out.println(enterName());
                        parse(scanner.nextLine());
                        turns++;
                }
        }
        private void welcome() {
                try {
                        String text = Files.readString(Path.of(bannerFilePath));
                        System.out.println(text);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public void gameInstructions() {
                System.out.println("You must use the following commands to navigate: " +
                        "\"go [direction(North, West, South, East)]\" to move & " +
                        "\"get [item]\" to retrieve an item");

        }
        private String enterName() {
                System.out.println("Please enter your name: ");
                String playerName = scanner.nextLine();
                return ("Ciao " + playerName);
        }

        private void quitGame() {
                System.out.println("You'll always have a pizza our heart ... Goodbye!");
                setGameOver(true);
                System.exit(0);
        }
        private void parse(String userInput) {
                //takes in user input and then splits it on the spaces. Logic comes later
                List<String> parsedUserInput = new ArrayList<>(Arrays.asList(userInput.toLowerCase().split(" ")));
                //after we break up the user iput send it to be process
                processCommands(parsedUserInput);
        }

        //take the processed command and the delegates this to another
        private void processCommands(List<String> verbAndNounList){
                String noun = verbAndNounList.get(1);
                String verb = verbAndNounList.get(0);
                switch (verb) {
                        case "quit":
                                quitGame();
                                break;
                        case "go":
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
                                if(itemList.contains(noun)){
                                        player.look(new Item(noun));
                                }
                                else{
                                        player.look(player.getLocation());
                                }
                                break;
                        case "take":
                                //add item to inventory
                                player.addToInventory(noun);
                                break;
                        case "give":
                                //removes item from inventory
                                player.removeFromInventory(noun);
                                break;
                        case "inventory":
                                //prints inventory
                                Set<Item> tempInventory = player.getInventory();
                                System.out.println("Items in the Inventory");
                                for (Item item : tempInventory) {
                                        System.out.println(item.getName());
                                }
                                break;
                        default:
                                System.out.println("I don't understand");
                                break;
                }
        }


}