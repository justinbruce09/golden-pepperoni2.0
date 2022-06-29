package com.games.pizzaquest.app;

import com.games.pizzaquest.objects.*;
import com.games.pizzaquest.textparser.TextParser;
import com.games.pizzaquest.util.PizzaPrinter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class PizzaQuestApp {

        public static void setScanner(Scanner scanner) {
                PizzaQuestApp.scanner = scanner;
        }

        //scanner for the game
        static Scanner scanner = new Scanner(System.in);
        //text parser for users to use
        //path for some ascii art
        private static final String bannerFilePath = "WelcomeSplash.txt";
        private static final String npcFilePath = "npc.json";
        private static final String locationFilePath = "gamemap.json";
        private static final String textFilePath = "instructions.json";
        private static final String itemFilePath = "items.json";
        private static final PizzaPrinter inventoryPrinter = PizzaPrinter.SOUT;
        private static final PizzaPrinter questPrinter = PizzaPrinter.SOUT;
        private static final PizzaPrinter locationPrinter = PizzaPrinter.SOUT;
        private static final PizzaPrinter welcomePrinter = PizzaPrinter.SOUT;
        public static final PizzaPrinter resultPrinter = PizzaPrinter.SOUT;
        private static final PizzaPrinter turnPrinter = PizzaPrinter.SOUT;
        private static final PizzaPrinter reputationPrinter = PizzaPrinter.SOUT;
        public static final PizzaPrinter helpPrinter = PizzaPrinter.SOUT;


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
        private TextParser parser;

        public void execute() {
                initialize();
                welcomePrinter.println(enterName(scanner.nextLine()));
                while(turns < END_OF_TURNS) {
                        //send user input to parser to validate and return a List
                        //then runs logic in relation to the map, and list based on Noun Verb Relationship
                        turnLogic(scanner.nextLine());

                }
                quitGame();
        }

        public void initialize() {
                parser = new TextParser();
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
                welcomePrinter.println("Please enter your name: ");

        }

        public void turnLogic(String input) {
                processCommands(parser.parse(input));
                checkIfGameIsWon();
                // Increment turns by 1
                //Display player status including number of turns left
                int turnsLeft = END_OF_TURNS - turns;
                turnPrinter.println("It's day " + turns + ". You have " + turnsLeft + " days left." );
                //Players reputation is displayed whenever status is updated
                reputationPrinter.println("Your reputation is " + reputation);
        }


        private void checkIfGameIsWon() {
                if(reputation >=WINNING_REPUTATION){
                        resultPrinter.println("You win");
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
                        welcomePrinter.print(textBuilder + "\n");
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void gameInstructions() {
                        gameTexts.displayCommands();

        }

        public String enterName(String nameInput) {
                String playerName = nameInput;
                return ("Ciao " + playerName+ " you are in " + gamestate.getPlayerLocation());
        }

        private void quitGame() {
                welcomePrinter.println("You'll always have a pizza our heart ... Goodbye!");
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
                                travel(noun, validDirections);
                                break;
                        case "look":
                                //look(); //player location or item  description printed
                                //will need a item list and a location list
                                //todo - check size and get last
                                //if room, do the first, else if item, do the second
                                look(noun);
                                break;
                        case "take":
                                takeItem(noun);
                                break;
                        case "talk":
                                //talk to NPC to inventory
                                talk(noun);
                                break;
                        case "give":
                                giveItem(noun);
                                break;
                        case "inventory":
                                printInventory();
                                break;
                        case "help":
                                gameInstructions();
                                break;
                        case "reset":
                                resetGame();
                                break;
                        default:
                                resultPrinter.print("I don't understand " + verbAndNounList + "\n");
                                resultPrinter.println("Type help if you need some guidance on command structure!");
                                break;
                }
        }

        private void giveItem(String noun) {
                //removes item from inventory
                if (!player.getInventory().removeIf(item -> item.getName().equals(noun))){
                        return;
                }
                if(gamestate.getPlayerLocation().npc!=null){
                       reputation += gamestate.getPlayerLocation().npc.processItem(noun);
                }
                player.removeFromInventory(noun);
        }

        private void takeItem(String noun) {
                if(gamestate.getPlayerLocation().getItems().removeIf(item -> item.getName().equals(noun))) {
                        //add item to inventory
                        player.addToInventory(noun);
                        printInventory();
                        resultPrinter.println("Items in location: " + gamestate.getPlayerLocation().getItems());
                } else {
                        resultPrinter.println("Item " + noun + " not found in " + gamestate.getPlayerLocation());
                }
        }

        private void look(String noun) {

                if (noun.equals("")){
                        return;
                }
                List<Item> filteredItems = itemsList.stream().filter(item -> item.getName().equals(noun))
                        .collect(Collectors.toList());
                if(!filteredItems.isEmpty()){
                        resultPrinter.println(filteredItems.get(0).getDescription());
                }else if (gamestate.getPlayerLocation().npc!= null && gamestate.getPlayerLocation().npc.getName().equals(noun)){
                        resultPrinter.println(gamestate.getPlayerLocation().npc.getNpcDescription());
                }
                else{
                        resultPrinter.println(player.look(gamestate.getPlayerLocation()));
                }
                return;
        }

        private void travel(String noun, ArrayList<String> validDirections) {
                if (noun.equals("") || !validDirections.contains(noun)){
                        resultPrinter.print("There is nothing that way!\n");
                        return;
                }
                String nextLoc = gamestate.getPlayerLocation().getNextLocation(noun);
                locationPrinter.print("\n");
                if(!nextLoc.equals("nothing")){
                        locationPrinter.println(nextLoc + "\n");
                        gamestate.setPlayerLocation(gameMap.get(nextLoc.toLowerCase()));
                        locationPrinter.print("\n");
                        locationPrinter.print(player.look(gamestate.getPlayerLocation()) + "\n");
                        locationPrinter.println("\n");
                        turns++;
                }
                else{
                        resultPrinter.println("There is nothing that way!");
                }
        }

        private void printInventory() {
                Set<Item> tempInventory = player.getInventory();
                inventoryPrinter.print("Items in the Inventory\n");
                for (Item item : tempInventory) {
                        inventoryPrinter.print(item.getName() + "\n");
                }
                inventoryPrinter.println();
        }

        private void talk(String noun) {
                Location playerLocation = gamestate.getPlayerLocation();
                if(playerLocation.npc != null && playerLocation.npc.getName().equals(noun)){
                        questPrinter.print(playerLocation.npc.giveQuest() + "\n");
                }else{
                        resultPrinter.println("That player many not be in in this room or even exist!");
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