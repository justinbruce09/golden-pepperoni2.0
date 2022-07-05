package com.games.pizzaquest.app;

import com.games.pizzaquest.objects.Gamestate;
import com.games.pizzaquest.objects.Item;
import com.games.pizzaquest.objects.Location;
import com.games.pizzaquest.objects.NonPlayerCharacter;
import org.junit.Test;

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
        assertEquals(1, pizzaQuestApp.player.getInventory().size());
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
    public void takeItem_addsNothingToInventory_whenItemNotInLocation() throws Exception {
        pizzaQuestApp.takeItem("spoon");
        assertEquals(0, pizzaQuestApp.player.getInventory().size());
        assertEquals(3, pizzaQuestApp.gamestate.getPlayerLocation().getItems().size());
    }

    @Test
    public void takeItem_removesNothingFromLocation_whenItemNotInLocation() {
        pizzaQuestApp.takeItem("spoon");
        assertEquals(3, pizzaQuestApp.gamestate.getPlayerLocation().getItems().size());
    }

    @Test
    public void takeItem_takesOnlyOneOfItemIfItemHasSameName_whenItemInSameLocation() {
        pizzaQuestApp.takeItem("coin");
        //tests to ensure take brought room inventory from 3 -> 2
        assertEquals(2, pizzaQuestApp.gamestate.getPlayerLocation().getItems().size());
        //ensures player inventory item only at 1 (i.e. took 1 of 2 coins)
        assertEquals(1,pizzaQuestApp.player.getInventory().size());
        // true if item in inventory has name "coin"
        assertTrue(pizzaQuestApp.player.getInventory().removeIf(item -> item.getName().equals("coin")));
        // shows 1 of 2 "coin" items still in remove after taking a single coin
        assertTrue(pizzaQuestApp.gamestate.getPlayerLocation().getItems().removeIf(item -> item.getName().equals("coin")));
        // done to demonstrate "pizza" still exists in room
        assertTrue(pizzaQuestApp.gamestate.getPlayerLocation().getItems().removeIf(item -> item.getName().equals("pizza")));
    }

    @Test
    public void takeItem_takesRequestedItem_whenProperNameUsed() {
        pizzaQuestApp.takeItem("pizza");
        assertEquals(2, pizzaQuestApp.gamestate.getPlayerLocation().getItems().size());
        assertEquals(1,pizzaQuestApp.player.getInventory().size());
        assertTrue(pizzaQuestApp.player.getInventory().removeIf(item -> item.getName().equals("pizza")));
    }

    @Test
    public void takeItem_doesNotTakeRequestedItem_whenMisspelled() {
        pizzaQuestApp.takeItem("peet-za");
        assertEquals(3, pizzaQuestApp.gamestate.getPlayerLocation().getItems().size());
        assertNotEquals(1, pizzaQuestApp.player.getInventory().size());
        assertFalse(pizzaQuestApp.player.getInventory().removeIf(item -> item.getName().equals("peet-za")));
        assertFalse(pizzaQuestApp.player.getInventory().removeIf(item -> item.getName().equals("pizza")));
    }
}