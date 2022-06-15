package com.games.pizzaquest.objects;

import java.util.HashSet;
import java.util.Set;

public interface PlayerInterface {
    Set<Item> inventory = new HashSet<>() ;
    void setName(String name);
    String getName();

}
