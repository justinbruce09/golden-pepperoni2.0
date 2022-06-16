package com.games.pizzaquest.app;
import com.games.pizzaquest.objects.*;
import com.games.pizzaquest.textparser.TextParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        private final NonPlayerCharacter npc1 = new NonPlayerCharacter("tony", "Come guess me this riddle, what beats pipe and fiddle\n" +
                "What's hotter than mustard and milder than cream\n" +
                "What best wets your whistle, what's clearer than crystal\n" +
                "Sweeter than honey and stronger than steam");
        private final Location location =  new Location("Naples", npc1, "nothing", "Rome", "nothing", "nothing");

        private final NonPlayerCharacter npc2 = new NonPlayerCharacter("momma_mozzarella", "I want a coin");

        public final Gamestate gamestate = new Gamestate(location);
        public final Player player = new Player(inventory);


        //keep the game running until win/lose condition is met
        private boolean isGameOver = false;

        Hashtable<String, Location> mapped;
        List<Location> locationList;
        Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();



        public List<Location> getLocationListFromJson(){
                ArrayList<Location> locationList = new ArrayList<>();
                try{
                        Gson gson = new Gson();
                        Reader reader = Files.newBufferedReader(Paths.get("resources/gamemap.json"));
                        locationList = gson.fromJson(reader, locationListType);
                        for(Location loc: locationList){
                                System.out.println(loc.getName());
                        }
                        reader.close();

                }
                catch(Exception e){
                        e.printStackTrace();
                }
                return locationList;
        }

        public Hashtable<String, Location> hashNewMap(List<Location> initialMap) {
                Hashtable<String, Location> newMap = new Hashtable<>();
                for(Location location: initialMap){
                        newMap.put(location.getName(), location);
                }
                return newMap;
        }



        public void execute() {
                TextParser parser = new TextParser();
                setGameOver(false);
                //temporary setting of decription for npc
                npc1.setNpcDescription("Tony is covered in flour and looks like he wants to speak to you!");
                //temporarily put in a 1 iteration loop to test user input

                //temporarily put in a 4 iteration loop to test user input
                welcome();

//                locationList = gson.fromJson(jsonData, locationListType);
                locationList = getLocationListFromJson();
                mapped = hashNewMap(locationList);

                System.out.println(enterName());
                while(turns < END_OF_TURNS) {
                        //send user input to parser to validate and return a List
                        //then runs logic in relation to the map, and list based on Noun Verb Relationship
                        processCommands(parser.parse(scanner.nextLine()), map, locationList); ;
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

        private void gameInstructions() {
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

        private void quitGame() {
                System.out.println("You'll always have a pizza our heart ... Goodbye!");
                setGameOver(true);
                System.exit(0);
        }

        private void resetGame() {
                setGameOver(true);
                turns = 0;
                execute();
        }

        //take the processed command and the delegates this to another
        private void processCommands(List<String> verbAndNounList, Hashtable<String, Location> map, List<Location> newMap){
                String noun = verbAndNounList.get(verbAndNounList.size()-1);
                String verb = verbAndNounList.get(0);

                switch (verb) {
                        case "quit":
                                quitGame();
                                break;
                        case "go":
                                if (noun.equals("")){
                                        break;
                                }
                                String nextLoc = gamestate.getPlayerLocation().getNextLocation(noun);
                                System.out.println();
                                if(!nextLoc.equals("nothing")){
                                        System.out.println(nextLoc);
                                        gamestate.setPlayerLocation(mapped.get(nextLoc.toLowerCase()));
                                        System.out.println();
                                        System.out.println(player.look(gamestate.getPlayerLocation()));
                                        System.out.println();
                                }
                                else{
                                        System.out.println("There is nothing that way!");
                                }
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
                                }else if (gamestate.getPlayerLocation().npc!= null && gamestate.getPlayerLocation().npc.getName().equals(noun)){
                                        System.out.println(gamestate.getPlayerLocation().npc.getNpcDescription());
                        }
                                else{
                                        System.out.println(player.look(gamestate.getPlayerLocation()));
                                }
                                break;
                        case "take":
                                //add item to inventory
                                player.addToInventory(noun);
                                break;
                        case "talk":
                                //add item to inventory
                                talk(noun);
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
                                System.out.printf("I don't understand '%s'%n", verbAndNounList);
                                System.out.println("Type help if you need some guidance on command structure!");;
                                break;
                }
        }

        private void talk(String noun) {
                Location playerLocation = gamestate.getPlayerLocation();
                if(playerLocation.npc != null && playerLocation.npc.getName().equals(noun)){
                        System.out.println(playerLocation.npcTalk());
                }else{
                        System.out.println("That player many not be in in this room or even exist!");
                }
        }

}