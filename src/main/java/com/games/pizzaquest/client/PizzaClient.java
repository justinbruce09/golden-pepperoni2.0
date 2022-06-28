package com.games.pizzaquest.client;
import com.games.pizzaquest.app.PizzaQuestApp;

import java.util.Scanner;

public class PizzaClient {
    public static void main(String[] args) {
            PizzaQuestApp app = new PizzaQuestApp();
            PizzaQuestApp.setScanner(new Scanner(System.in));
            app.execute();
    }
}