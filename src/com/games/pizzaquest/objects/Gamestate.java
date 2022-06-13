package com.games.pizzaquest.objects;

public class Gamestate {
    Location playerLocation;

    public Gamestate(Location playerLocation){
        this.playerLocation = playerLocation;
    }

    public Location getPlayerLocation() {
        return playerLocation;
    }

    public void setPlayerLocation(Location playerLocation){
        this.playerLocation = playerLocation;
    }
}