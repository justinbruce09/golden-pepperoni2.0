package com.games.pizzaquest.client.app;
import com.games.pizzaquest.textparser.TextParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class PizzaQuestApp {

        //scanner for the game
        static Scanner scanner = new Scanner(System.in);
        //text parser for users to use
        //path for some ascii art
        private static final String bannerFilePath = "data/WelcomeSplash.txt";
        private static final String helpFilePath = "data/Instructions.txt";

        //track turn may be moved to player
        private int turns = 0;
        static final int END_OF_TURNS=1;

        public boolean isGameOver() {
                return isGameOver;
        }

        public void setGameOver(boolean gameOver) {
                isGameOver = gameOver;
        }

        //keep the game running until win/lose condition is met
        private boolean isGameOver = false;
        private String userInput;


        public void execute() {
                //temporarily put in a 1 iteration loop to test user input
                while(turns < END_OF_TURNS) {
                        welcome();
//                        gameInstructions();
                        System.out.println();
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

                switch (verbAndNounList.get(0)) {
                        case "quit":
                                quitGame();
                                break;
                        case "go":
                                //go(verbAndNounList); // send to method to process next part of command

                                break;
                        case "look":
                                //look(); //player location or item  description printed

                                break;
                        case "take":
                                //addItem(); //take item from room if possible

                                break;
                        case "give":
                                //removeItem();
                                break;
                        case "inventory":
                                //printInventory(); // prints your inventory

                                break;
                        case "help":
                                gameInstructions();
                                break;
                        default:
                                System.out.println("I don't understand");
                                break;
                }
        }


}