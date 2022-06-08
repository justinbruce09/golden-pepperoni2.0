package com.games.pizzaquest.client;

import com.games.pizzaquest.objects.Location;
import com.games.pizzaquest.objects.Player;

import java.util.Collections;

class main {
    public static void main(String[] args) {
        System.out.println("HelloWorld");
    }
    Location location = new Location("Colosseum");
    Player player = new Player(Collections.emptySet(), location);
}