package com.games.pizzaquest.client;

import com.games.pizzaquest.client.app.PizzaQuestApp;

class main {
    public static void main(String[] args) {
        System.out.println("HelloWorld");
        PizzaQuestApp app = new PizzaQuestApp();
        app.execute();
        app.gameInstructions();
    }
}