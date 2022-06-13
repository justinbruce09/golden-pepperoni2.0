package com.games.pizzaquest.app;
import com.games.pizzaquest.objects.Item;
import com.games.pizzaquest.objects.Location;
import com.games.pizzaquest.objects.Player;
import com.games.pizzaquest.textparser.TextParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PizzaQuestApp {

        //scanner for the game
        static Scanner scanner = new Scanner(System.in);
        //text parser for users to use
        //path for some ascii art
        private static final String bannerFilePath = "resources/WelcomeSplash.txt";
        private static final String helpFilePath = "resources/Instructions.txt";

        //track turn may be moved to player
        private int turns = 0;
        static final int END_OF_TURNS=10;

        public boolean isGameOver() {
                return isGameOver;
        }

        public void setGameOver(boolean gameOver) {
                isGameOver = gameOver;
        }
        public final List<String> itemList = List.of("pizza_cutter", "prosciutto", "wine_glass", "lemons", "coin", "ancient_pizza_cookbook", "moped", "cannoli", "marble_sculpture", "espresso");

        //Initial State of the Player, inventory and starting location
        public final Set<Item> inventory = new HashSet<>();
        String[] rooms = {"rome", "paris" ,"nyc", "la"};
        public Location location =  new Location("Naples", rooms);
        public Player player = new Player(inventory, location);

        //keep the game running until win/lose condition is met
        private boolean isGameOver = false;
        private String currentInput;


        public void execute() {
                TextParser parser = new TextParser();
                setGameOver(false);
                //temporarily put in a 1 iteration loop to test user input

                //temporarily put in a 4 iteration loop to test user input
                welcome();
                System.out.println(enterName());

                while(turns < END_OF_TURNS) {

                        parser.parse(scanner.nextLine());
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
                return ("Ciao " + playerName+ " you are in " + player.getLocation());
        }

        public void quitGame() {
                System.out.println("You'll always have a pizza our heart ... Goodbye!");
                setGameOver(true);
                System.exit(0);
        }

        public void resetGame() {
                setGameOver(true);
                turns = 0;
                execute();
        }
}