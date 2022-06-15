package com.games.pizzaquest.objects;

import java.util.*;

public class Location {
    private final String name;
    private final Hashtable<String, String> adjLocations = new Hashtable<>();
    public NonPlayerCharacter npc = null;
    private String north;
    private String east;
    private String south;
    private String west;

    public String getNorth() {
        return north;
    }

    public String getEast() {
        return east;
    }

    public String getSouth() {
        return south;
    }

    public String getWest() {
        return west;
    }

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

    public String getNextLocation(String direction){
        String nextLoc = null;
        switch (direction.toLowerCase()){
            case "north":
                nextLoc = getNorth();
                break;
            case "east":
                nextLoc = getEast();
                break;
            case "south":
                nextLoc = getSouth();
                break;
            case "west":
                nextLoc = getWest();
                break;
            default:
                break;
        }
        return nextLoc;
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

    public String npcInRoom(NonPlayerCharacter npc){
        String npcStatus="There is no one in this room";
        if(npc!=null){
            npcStatus="Standing in " + name+ " is "+ npc.getName();
        }
        return npcStatus;
    }
    public String npcTalk(){
        return npc.giveQuest();
    }

    @Override
    public String toString(){
        return "You are in the " + getName() + "." + printBoarders() +"\n"+ npcInRoom(npc);
    }


}