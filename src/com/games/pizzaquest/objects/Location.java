package com.games.pizzaquest.objects;

import java.util.*;

public class Location {
    private final String name;
    private final Hashtable<String, String> adjLocations = new Hashtable<>();
    public NonPlayerCharacter npc = null;


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
            }else{
                adjLocations.put(directions[i], "a stone wall");
            }
        }
    }

    public Hashtable<String, String> getAdjLocations() {
        return adjLocations;
    }

    private String printBoarders(){
     return "\nTo the north we have " + adjLocations.get("north") + ",\nTo the east we have " + adjLocations.get("east")+
            ",\nTo the south we have " + adjLocations.get("south") + ",\nTo the west we have " + adjLocations.get("west")+".";
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString(){
        return "You are in the " + getName() + "." + printBoarders() +"\n" +
                (npc != null?npc.getName()+" is standing in the room": "there is no one in the room");
    }


}