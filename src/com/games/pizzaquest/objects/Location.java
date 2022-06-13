package com.games.pizzaquest.objects;

import java.util.*;

public class Location {
    private final String name;
    private final Hashtable<String, String> adjLocations = new Hashtable<>();
    NonPlayerCharacter npc = null;


    public Location (String name, String... locations){
        this.name = name;
        setBoarderRooms(locations);
    }

    public Location (String name, NonPlayerCharacter NPC,String... locations){
        this.name = name;
        setBoarderRooms(locations);
        this.npc = NPC;
    }
    private void setBoarderRooms(String... locations){
        String[] directions = {"north", "east", "south", "west"};
        for (int i = 0 ; i <locations.length ; i++){
            adjLocations.put(directions[i],"");
            if(!locations[i].equals("")){
                adjLocations.put(directions[i], locations[i]);
            }
        }
    }

    public Hashtable<String, String> getAdjLocations() {
        return adjLocations;
    }

    private String printBoarders(){
     return "\nto the north we have " + adjLocations.get("north") + "\n to the east we have " + adjLocations.get("east")+
             "\nto the south we have " + adjLocations.get("south") + "\n to the west we have " + adjLocations.get("west")+".";
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "You are in the " + getName() + "." + printBoarders();
    }


}