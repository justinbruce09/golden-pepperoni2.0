package com.games.pizzaquest.objects;

public class Location {
    String name;

    public Location (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "You are in the " + getName() + ".";
    }
}