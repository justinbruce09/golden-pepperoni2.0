package com.games.pizzaquest.client.app;
import com.games.pizzaquest.textparser.TextParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class PizzaQuestApp {

        //scanner for the game
        private Scanner scanner = new Scanner(System.in);
        //text parser for users to use
        TextParser tp = new TextParser();
        //path for some ascii art
        private static final String bannerFilePath = "data/WelcomeSplash.txt";
        //track turn may be moved to player
        private int turns = 0;
        //keep the game running until win/lose condition is met
        private boolean isGameOver = false;
        private String userInput;


        public void execute() {
                //temporaily put in a 1 iteration loop to test user input
                while(turns <1) {
                        welcome();
                        gameInstructions();
                        System.out.println(enterName());
                        tp.parse(scanner.nextLine());
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
                isGameOver = true;
                System.exit(0);
        }

}