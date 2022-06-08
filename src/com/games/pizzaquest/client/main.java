package com.games.pizzaquest.client;

<<<<<<< HEAD
import com.games.pizzaquest.objects.Location;
import com.games.pizzaquest.objects.Player;

import java.util.Collections;
=======
import com.games.pizzaquest.client.app.PizzaQuestApp;
>>>>>>> dev

class main {
    public static void main(String[] args) {
        System.out.println("HelloWorld");
        PizzaQuestApp app = new PizzaQuestApp();
        app.execute();
    }
    Location location = new Location("Colosseum");
    Player player = new Player(Collections.emptySet(), location);
}