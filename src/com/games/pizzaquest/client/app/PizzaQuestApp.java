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
        private static final String helpFilePath = "data/Instructions.txt";

        //track turn may be moved to player
        private int turns = 0;
        static final int END_OF_TURNS=10;

        public boolean isGameOver() {
                return isGameOver;
        }

        public void setGameOver(boolean gameOver) {
                isGameOver = gameOver;
        }
        private final List<String> itemList = List.of("pizza_cutter", "prosciutto", "wine_glass", "lemons", "coin", "ancient_pizza_cookbook", "moped", "cannoli", "marble_sculpture", "espresso");

        //Initial State of the Player, inventory and starting location
        private final Set<Item> inventory = new HashSet<>();
        private final Location location =  new Location("Naples");
        private final Player player = new Player(inventory, location);

        //keep the game running until win/lose condition is met
        private boolean isGameOver = false;
        private String userInput;


        public void execute() {

                setGameOver(false);
                //temporarily put in a 1 iteration loop to test user input

                //temporarily put in a 4 iteration loop to test user input
                welcome();
                System.out.println(enterName());

                while(turns < END_OF_TURNS) {
                        parse(scanner.nextLine());
                        turns++;
                        System.out.println(turns);
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
                try {
                        String text = Files.readString(Path.of(helpFilePath));
                        System.out.println(text);
                } catch (IOException e) {
                        e.printStackTrace();
                }

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
                                System.out.println("I don't understand");
                                break;
                }
        }

        private void resetGame() {
                setGameOver(true);
                turns = 0;
                execute();
        }
}