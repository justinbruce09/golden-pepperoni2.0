package com.games.pizzaquest.objects;

public class Item {
    String name;
    String type;
    String room;

    public Item (String name) {
        this.name = name;
    }

    public Item (String name, String type, String room){
        this.name = name;
        this.type = type;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "This is a " + getName() + ".";
    }
}