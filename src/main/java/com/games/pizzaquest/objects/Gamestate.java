package com.games.pizzaquest.objects;

public class Gamestate {
    private Location playerLocation;
    private Player player;

    public Gamestate(Location playerLocation){
        this.playerLocation = playerLocation;
    }

    public Gamestate(Location playerLocation, Player player){
        this(playerLocation);
        this.player = player;
    }

    public Location getPlayerLocation() {
        return this.playerLocation;
    }

    public void setPlayerLocation(Location playerLocation){
        this.playerLocation = playerLocation;
    }

}