package com.games.pizzaquest.objects;

import java.util.List;

public class Location {
    private String name;
    private List<Location> adjacentLocations;

    public Location (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return "You are in the " + getName() + ".";
    }


}