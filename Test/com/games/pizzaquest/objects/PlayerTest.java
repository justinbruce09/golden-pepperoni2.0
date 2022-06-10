package com.games.pizzaquest.objects;
import org.junit.Test;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void look_shouldReturnLocationInfo_whenPlayerCallsLookOnLocation(){
        Location location = new Location("Colosseum");
        Player player = new Player(Collections.emptySet(), location);
        assertEquals(player.look(location), "You are in the Colosseum.");
    }

    @Test
    public void look_shouldReturnItemInfo_whenPlayerCallsLookOnItem(){
        Item item = new Item("Pizza Cutter");
        Location location = new Location("Colosseum");
        Player player = new Player(Collections.emptySet(), location);
        assertEquals(player.look(item), "This is a Pizza Cutter.");
    }

}
