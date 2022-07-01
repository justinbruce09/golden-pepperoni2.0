package com.games.pizzaquest.app;

import com.games.pizzaquest.objects.Gamestate;
import com.games.pizzaquest.objects.Item;
import com.games.pizzaquest.objects.Location;
import com.games.pizzaquest.objects.NonPlayerCharacter;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PizzaQuestAppTest {

    PizzaQuestApp pizzaQuestApp;
    NonPlayerCharacter npc;
    NonPlayerCharacter npc2;

    @org.junit.Before
    public void setup(){
        pizzaQuestApp = new PizzaQuestApp();
        pizzaQuestApp.gamestate = new Gamestate(new Location("naples", "nothing", "nothing", "nothing", "nothing"));
        pizzaQuestApp.gamestate.getPlayerLocation().setItems(new ArrayList<>());
        pizzaQuestApp.gamestate.getPlayerLocation().getItems().add(new Item("coin".toLowerCase()));
        pizzaQuestApp.gamestate.getPlayerLocation().getItems().add(new Item("pizza"));
        pizzaQuestApp.gamestate.getPlayerLocation().getItems().add(new Item("coin"));
        npc = new NonPlayerCharacter("dave", "meh", "naples", "derp", "20", "coin", "trophy");
        npc2 = new NonPlayerCharacter("dave", "meh", "naples", "derp", "20", "coin");
    }

    @org.junit.Test
    public void giveItemToNpc_changesNothing_whenItemNotInInventory() throws Exception {
        pizzaQuestApp.gamestate.getPlayerLocation().setNpc(npc);
        pizzaQuestApp.takeItem("coin");
        pizzaQuestApp.giveItemToNpc("pizza");
        assertEquals(1, pizzaQuestApp.player.getInventory().size());
        assertTrue(pizzaQuestApp.player.getInventory().removeIf(item -> item.getName().equals("coin")));
        assertFalse(pizzaQuestApp.player.getInventory().removeIf(item -> item.getName().equals("trophy")));
        assertEquals(0, pizzaQuestApp.getReputation());
    }

    @org.junit.Test
    public void giveItemToNpc_removesOneItemFromInventory_whenItemIsInInventory_andWantedByNpc(){
        pizzaQuestApp.gamestate.getPlayerLocation().setNpc(npc2);
        pizzaQuestApp.takeItem("coin");
        pizzaQuestApp.takeItem("coin");
        pizzaQuestApp.giveItemToNpc("coin");
        assertEquals(1, pizzaQuestApp.player.getInventory().size());
        assertTrue(pizzaQuestApp.player.getInventory().removeIf(item -> item.getName().equals("coin")));
    }

    @org.junit.Test
    public void giveItemToNpc_addsRewardToInventory_whenItemIsInInventory_andWantedByNpc(){
        pizzaQuestApp.gamestate.getPlayerLocation().setNpc(npc);
        pizzaQuestApp.takeItem("coin");
        pizzaQuestApp.giveItemToNpc("coin");
        assertEquals(2, pizzaQuestApp.player.getInventory().size());
        assertTrue(pizzaQuestApp.player.getInventory().removeIf(item -> item.getName().equals("trophy")));
    }

    @org.junit.Test
    public void giveItemToNpc_addsNothingToInventory_whenItemIsInInventory_andNoItemAwardedByNpc(){
        pizzaQuestApp.gamestate.getPlayerLocation().setNpc(npc2);
        pizzaQuestApp.takeItem("coin");
        pizzaQuestApp.takeItem("coin");
        pizzaQuestApp.giveItemToNpc("coin");
        assertEquals(1, pizzaQuestApp.player.getInventory().size());
        assertFalse(pizzaQuestApp.player.getInventory().removeIf(item -> item.getName().equals("trophy")));
    }

    @org.junit.Test
    public void giveItemToNpc_increasesReputation_whenItemIsInInventory_andWantedByNpc(){
        pizzaQuestApp.gamestate.getPlayerLocation().setNpc(npc);
        pizzaQuestApp.takeItem("coin");
        pizzaQuestApp.takeItem("coin");
        pizzaQuestApp.giveItemToNpc("coin");
        assertEquals(20, pizzaQuestApp.getReputation());
    }

    @org.junit.Test
    public void giveItemToNpc_decreasesReputation_whenItemIsInInventory_andNotWantedByNpc(){
        pizzaQuestApp.gamestate.getPlayerLocation().setNpc(npc);
        pizzaQuestApp.takeItem("pizza");
        pizzaQuestApp.giveItemToNpc("pizza");
        assertEquals(-20, pizzaQuestApp.getReputation());
    }


    @org.junit.Test
    public void takeItem_changesNothing_whenItemNotInLocation() throws Exception {
        pizzaQuestApp.takeItem("spoon");
        assertEquals(0, pizzaQuestApp.player.getInventory().size());
    }


}