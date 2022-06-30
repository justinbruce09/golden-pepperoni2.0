package com.games.pizzaquest.objects;

import java.util.Set;

public class Player {
    private final Set<Item> inventory;

    public Set<Item> getInventory() {
        return inventory;
    }

    public Player(Set<Item> inventory) {
        this.inventory = inventory;
    }

    public void addToInventory(String itemName) {
        inventory.add(new Item(itemName));
    }

    public boolean removeFromInventoryIf(String itemName) {
        return inventory.removeIf(item -> item.getName().equals(itemName));
    }

    public <T> String look(T thingToLookAt) {
        return thingToLookAt.toString();
    }

}