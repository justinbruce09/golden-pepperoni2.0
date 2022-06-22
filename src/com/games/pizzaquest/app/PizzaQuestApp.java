package com.games.pizzaquest.app;

import com.games.pizzaquest.objects.*;
import com.games.pizzaquest.textparser.TextParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PizzaQuestApp {

        //scanner for the game
        static Scanner scanner = new Scanner(System.in);
        //text parser for users to use
        //path for some ascii art
        private static final String bannerFilePath = "WelcomeSplash.txt";
        private static final String npcFilePath = "npc.json";
        private static final String locationFilePath = "gamemap.json";
        private static final String textFilePath = "instructions.json";
        private static final String itemFilePath = "items.json";

        //track turn may be moved to player
        private int turns = 0;
        static final int END_OF_TURNS=10;
        static final int WINNING_REPUTATION= 40;
        public final List<String> itemList = List.of("pizza_cutter","olive_oil", "prosciutto", "wine_glass", "lemons", "coin", "ancient_pizza_cookbook", "moped", "cannoli", "marble_sculpture", "espresso");

        //Initial State of the Player, inventory and starting location
        private final Set<Item> inventory = new HashSet<>();
        public  Gamestate gamestate =  null;
        public final Player player = new Player(inventory);

        private final ArrayList<NonPlayerCharacter> npcList= new ArrayList<NonPlayerCharacter>();
        private int reputation =0;

        GameTexts gameTexts = new GameTexts();



        //keep the game running until win/lose condition is met
        private boolean isGameOver = false;

        private Hashtable<String, Location> gameMap;
        private List<Location> locationList;
        private Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();
        private Type itemListType = new TypeToken<List<Item>>(){}.getType();

        private List<Item> itemsList;

        public void execute() {
                TextParser parser = new TextParser();
                setGameOver(false);
                //temporary setting of description for npc
                //temporarily put in a 1 iteration loop to test user input
                NpcGson();
                locationList = getLocationListFromJson();
                gameMap = hashNewMap(locationList);
                setNPC();
                GameTextGson();
                itemsList = getItemListFromJson();

                addItemsToLocationMap(gameMap, itemsList);
                welcome();
                gamestate = new Gamestate(gameMap.get("naples"));
                System.out.println(enterName());
                while(turns < END_OF_TURNS) {
                        //send user input to parser to validate and return a List
                        //then runs logic in relation to the map, and list based on Noun Verb Relationship

                        processCommands(parser.parse(scanner.nextLine()));
                        checkIfGameIsWon();
                        // Increment turns by 1
                        //Display player status including number of turns left
                        int turnsLeft = END_OF_TURNS - turns;
                        System.out.println("It's day " + turns + ". You have " + turnsLeft + " days left." );
                        //Players reputation is displayed whenever status is updated
                        System.out.println("Your reputation is " + reputation);

                }
                quitGame();
        }


        private void checkIfGameIsWon() {
                if(reputation >=WINNING_REPUTATION){
                        System.out.println("You win");
                        quitGame();
                }
        }

        private void welcome() {
                InputStream welcomeSplash = getFileFromResourceAsStream(bannerFilePath);
                StringBuilder textBuilder = new StringBuilder();
                try  (Reader reader = new BufferedReader(new InputStreamReader
                        (welcomeSplash, Charset.forName(StandardCharsets.UTF_8.name())))){
                        int c = 0;
                        while ((c = reader.read()) != -1) {
                                textBuilder.append((char) c);
                        }
                        System.out.println(textBuilder);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void gameInstructions() {
                        gameTexts.displayCommands();

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
        public boolean isGameOver() {
                return isGameOver;
        }

        public void setGameOver(boolean gameOver) {
                isGameOver = gameOver;
        }

        private void resetGame() {
                setGameOver(true);
                turns = 0;
                execute();
        }

        //take the processed command and the delegates this to another
        private void processCommands(List<String> verbAndNounList){
                String noun = verbAndNounList.get(verbAndNounList.size()-1);
                String verb = verbAndNounList.get(0);
                String person= "";
                ArrayList<String> validDirections= new ArrayList<String>();
                validDirections.add("north");
                validDirections.add("east");
                validDirections.add("west");
                validDirections.add("south");

                switch (verb) {
                        case "quit":
                                quitGame();
                                break;
                        case "go":
                                if (noun.equals("") || !validDirections.contains(noun)){
                                        System.out.println("There is nothing that way!");
                                        break;
                                }
                                String nextLoc = gamestate.getPlayerLocation().getNextLocation(noun);
                                System.out.println();
                                if(!nextLoc.equals("nothing")){
                                        System.out.println(nextLoc);
                                        gamestate.setPlayerLocation(gameMap.get(nextLoc.toLowerCase()));
                                        System.out.println();
                                        System.out.println(player.look(gamestate.getPlayerLocation()));
                                        System.out.println();
                                        turns++;
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
                                if(gamestate.getPlayerLocation().getItems().removeIf(item -> item.getName().equals(noun))) {
                                        //add item to inventory
                                        player.addToInventory(noun);
                                        System.out.println("Player inventory: " + player.getInventory());
                                        System.out.println("Items in location: " + gamestate.getPlayerLocation().getItems());
                                } else {
                                        System.out.println("Item " + noun + " not found in " + gamestate.getPlayerLocation());
                                }
                                break;
                        case "talk":
                                //talk to NPC to inventory
                                talk(noun);
                                break;
                        case "give":
                                //removes item from inventory
                                if (noun.equals("")){
                                        break;
                                }
                                if(gamestate.getPlayerLocation().npc!=null){
                                       reputation = gamestate.getPlayerLocation().npc.processItem(noun);
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
                                System.out.println("Type help if you need some guidance on command structure!");
                                break;
                }
        }

        private void talk(String noun) {
                Location playerLocation = gamestate.getPlayerLocation();
                if(playerLocation.npc != null && playerLocation.npc.getName().equals(noun)){
                        System.out.println(playerLocation.npc.giveQuest());
                }else{
                        System.out.println("That player many not be in in this room or even exist!");
                }
        }

        public void NpcGson(){
                        // create Gson instance
                        Gson gson = new Gson();
                        InputStream npcJSON = getFileFromResourceAsStream(npcFilePath);
                        // convert JSON file to map
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(npcJSON, "UTF-8"))){
                        Map<String, ArrayList<String>> map = gson.fromJson(reader, Map.class);
                        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                                ArrayList<String> JSONnpc = map.get(entry.getKey());
                                NonPlayerCharacter npc = new NonPlayerCharacter(entry.getKey(),JSONnpc.get(0),JSONnpc.get(1),JSONnpc.get(2),JSONnpc.get(3),JSONnpc.get(4));
                                npcList.add(npc);
                        }
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }
        public List<Location> getLocationListFromJson(){
                ArrayList<Location> locationList = new ArrayList<>();
                        Gson gson = new Gson();
                        InputStream locationJSON = getFileFromResourceAsStream(locationFilePath);
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(locationJSON, "UTF-8"))){
                        locationList = gson.fromJson(reader, locationListType);
                }
                catch(Exception e){
                        e.printStackTrace();
                }
                return locationList;
        }

        public List<Item> getItemListFromJson() {
                ArrayList<Item> itemsList = new ArrayList<>();
                        Gson gson = new Gson();
                InputStream locationJSON = getFileFromResourceAsStream(itemFilePath);
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(locationJSON, "UTF-8"))){
                        itemsList = gson.fromJson(reader, itemListType );
                }
                catch (IOException e) {
                        e.printStackTrace();
                }

                return itemsList;
        }

        public void addItemsToLocationMap(Hashtable<String, Location> gameMap, List<Item> itemsList){
                itemsList.forEach(item -> {
                        gameMap.get(item.getRoom().toLowerCase()).getItems().add(item);
                });
        }


        public void GameTextGson() {
                Gson gson = new Gson();
                InputStream locationJSON = getFileFromResourceAsStream(textFilePath);
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(locationJSON, "UTF-8"))){
                        // create Gson instance

                        // convert JSON file to GameTexts Object which contains the GameText
                        gameTexts = gson.fromJson(reader, GameTexts.class);


                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }


        public Hashtable<String, Location> hashNewMap(List<Location> initialMap) {
                Hashtable<String, Location> newMap = new Hashtable<>();
                for(Location location: initialMap){
                        location.setItems(new ArrayList<>());
                        newMap.put(location.getName(), location);
                }
                return newMap;
        }

        public void setNPC(){
                String tempNPCLocation = "";
                Location setNPCLocation= null;
                for (NonPlayerCharacter person:npcList
                     ) {
                        tempNPCLocation= person.getNpcLocation();
                        setNPCLocation= gameMap.get(tempNPCLocation);
                        if(setNPCLocation != null){
                        setNPCLocation.setNpc(person);}
                }

        }
        private static InputStream getFileFromResourceAsStream(String fileName) {
                ClassLoader classLoader = PizzaQuestApp.class.getClassLoader();
                InputStream inputStream = classLoader.getResourceAsStream(fileName);
                if (inputStream == null) {
                        throw new IllegalArgumentException("file not found! " + fileName);
                } else {
                        return inputStream;
                }
        }
}