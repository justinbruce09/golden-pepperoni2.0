package com.games.pizzaquest.objects;

import java.util.Set;

public class Player {
    private Set<String> inventory;
    private Location location;

    public Player(Set<String> inventory, Location location){
        this.inventory = inventory;
        this.location = location;
    }

    public <T> String look(T thingToLookAt){
        return thingToLookAt.toString();
    }

}