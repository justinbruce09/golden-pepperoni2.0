package com.games.pizzaquest.objects;

import java.util.Set;

public class Player {
    private final Set<Item> inventory;
    private final Location location;

    public Set<Item> getInventory() {
        return inventory;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(String locationName) {
        this.location.setName(locationName);
    }

    public Player(Set<Item> inventory, Location location){
        this.inventory = inventory;
        this.location = location;
    }

    public void addToInventory(String itemName){
        inventory.add(new Item(itemName));
    }

    public void removeFromInventory(String itemName){
        inventory.removeIf(item -> item.getName().equals(itemName));
    }

    public <T> String look(T thingToLookAt){
        return thingToLookAt.toString();
    }

}