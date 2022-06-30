package com.games.pizzaquest.objects;

public class Item {
    String name;
    String type;
    String room;
    String description;

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, String type, String room) {
        this.name = name;
        this.type = type;
        this.room = room;
    }

    public Item(String name, String type, String room, String description) {
        this.name = name;
        this.type = type;
        this.room = room;
        this.description = description;
    }

    // accessor methods
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRoom() {
        return room;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getName();
    }
}