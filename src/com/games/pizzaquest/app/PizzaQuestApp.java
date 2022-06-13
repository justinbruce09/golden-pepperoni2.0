package com.games.pizzaquest.app;
import com.games.pizzaquest.objects.*;
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
        public final Hashtable<String, Location> map = new Hashtable<>();

        //Initial State of the game


        //Initial State of the Player, inventory and starting location
        private final Set<Item> inventory = new HashSet<>();
        private final Location location =  new Location("Naples", "", "", "Rome", "");
        private NonPlayerCharacter npc1 = new NonPlayerCharacter("tony", "tony's quest");

        public final Gamestate gamestate = new Gamestate(location);
        public final Player player = new Player(inventory);


        //keep the game running until win/lose condition is met
        private boolean isGameOver = false;

        public void initMap(Hashtable<String, Location> map){
                Location naples = new Location("Naples", "", "", "Rome", "");
                Location rome = new Location("Rome", npc1,"Naples", "Tower", "Canal", "Pompeii");
                Location pompeii = new Location("Pompeii", "", "Rome","Trevi", "");
                Location trevi = new Location("Trevi", "Pompeii", "Canal","Cathedral", "");
                Location canal = new Location("Canal", "Rome", "Almafi","Cathedral", "Trevi");
                Location cathedral = new Location("Cathedral", "Trevi", "Canal","", "");
                Location almafi = new Location("Almafi", "Tower", "","", "Canal");
                Location towerOfPizza = new Location("Tower", "", "","Almafi", "Rome");
                map.put("naples", naples);
                map.put("rome", rome);
                map.put("pompeii", pompeii);
                map.put("trevi", trevi);
                map.put("canal", canal);
                map.put("cathedral", cathedral);
                map.put("almafi", almafi);
                map.put("tower", towerOfPizza);
        }

        public void execute() {
                TextParser parser = new TextParser();
                setGameOver(false);
                //temporarily put in a 1 iteration loop to test user input

                //temporarily put in a 4 iteration loop to test user input
                welcome();
                initMap(map);
                System.out.println(enterName());
                while(turns < END_OF_TURNS) {
                        parser.parse(scanner.nextLine(), map);
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
                return ("Ciao " + playerName+ " you are in " + gamestate.getPlayerLocation());
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