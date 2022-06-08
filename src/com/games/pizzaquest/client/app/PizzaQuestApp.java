package com.games.pizzaquest.client.app;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class PizzaQuestApp {

        //scanner for the game
        private Scanner scanner = new Scanner(System.in);
        //path for some ascii art
        private static final String bannerFilePath = "data/WelcomeSplash.txt";
        //track turn may be moved to player
        private int turns = 0;
        //keep the game running until win/lose condition is met
        private boolean isGameOver = false;
        private String userInput;


        public void execute() {
                if (!isGameOver) {
                        welcome();
                        System.out.println(enterName());
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
        private String enterName() {
                System.out.println("Please enter your name: ");
                String playerName = scanner.nextLine();
                return playerName;
        }

        private void quitGame() {
                System.out.println("You'll always have a pizza our heart ... Goodbye!");
                isGameOver = true;
                System.exit(0);
        }

}