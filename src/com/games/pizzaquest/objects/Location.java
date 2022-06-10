package com.games.pizzaquest.objects;

import java.io.DataInput;
import java.util.*;

public class Location {
    private String name;
    private List<Location> adjacentLocations;

    Hashtable<Integer, String> boarderRooms = new Hashtable<Integer,String >();



    public Location (String name, String[] adjRooms){
        this.name = name;
        boarderRooms.put(0, ""); //north
        boarderRooms.put(1, ""); //east
        boarderRooms.put(2, ""); //south
        boarderRooms.put(3, ""); //west
        setBoarderRooms(adjRooms);
    }

    private void setBoarderRooms(String[] boarders){
        for (int i = 0 ; i <boarders.length ; i++){
            boarderRooms.put(i,boarders[i]);
        }
    }

   /* public void setAdjacentLocations(String... locations) {
        adjacentLocations = new ArrayList<>();
        for (String location : locations) {
            this.adjacentLocations.add(new Location(location));
        }
    }*/

 private String printBoarders(){
     return "\nto the north we have " + boarderRooms.get(0)+ "\n to the east we have " + boarderRooms.get(1)+
             "\nto the south we have " + boarderRooms.get(2)+"\n to the west we have " + boarderRooms.get(3)+".";
   /*  System.out.println("to the east we have" + boarderRooms.get(1));
     System.out.println("to the south we have" + boarderRooms.get(2));
     System.out.println("to the west we have" + boarderRooms.get(3));*/
 }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return "You are in the " + getName() + "." +printBoarders();
    }


}