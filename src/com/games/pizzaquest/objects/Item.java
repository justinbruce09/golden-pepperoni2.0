package com.games.pizzaquest.objects;

public class Item {
    String name;

    public Item (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "This is a " + getName() + ".";
    }
}