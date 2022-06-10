package com.games.pizzaquest.objects;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private List<Location> adjacentLocations;

    public Location (String name, String... locations){
        this.name = name;
        setAdjacentLocations(locations);
    }

    public void setAdjacentLocations(String... locations) {
        adjacentLocations = new ArrayList<>();
        for (String location : locations) {
            this.adjacentLocations.add(new Location(location));
        }
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