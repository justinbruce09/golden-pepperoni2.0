package com.games.pizzaquest.objects;

public class Gamestate {
    private Location playerLocation;

    public Gamestate(Location playerLocation){
        this.playerLocation = playerLocation;
    }

    public Location getPlayerLocation() {
        return this.playerLocation;
    }

    public void setPlayerLocation(Location playerLocation){
        this.playerLocation = playerLocation;
    }
}