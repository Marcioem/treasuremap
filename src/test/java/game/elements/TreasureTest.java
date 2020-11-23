package game.elements;

import game.adventurers.Adventurer;
import game.map.elements.Treasure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreasureTest {

    @Test
    public void creationTreasureTest(){
        //Try to create a treasure without 0 treasure
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Treasure(3,1, 0);
        });
        String expectedMessage = "<= 0";
        assertTrue(exception.getMessage().contains(expectedMessage));

        //Try to create a treasure in negative positions
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Treasure(0,-1, 4);
        });
        expectedMessage = "< 0";
        assertTrue(exception.getMessage().contains(expectedMessage));

        //Create a treasure without exceptions
        Treasure treasure = new Treasure(3,0, 4);
        assertEquals("T - 3 - 0 - 4", treasure.toString());
    }

    @Test
    public void landAndLeaveTreasureTest(){
        //Create a treasure without exceptions
        Treasure treasure = new Treasure(3,0, 2);
        Adventurer adventurer = new Adventurer("John",2,0, "N", "AAGDAAD");

        assertEquals(0, adventurer.getPickedTreasures());

        //The adventure occupy the cell, steal a treasure
        treasure.land(adventurer);
        assertTrue(treasure.hasTreasures());
        assertTrue(treasure.isOccupied());
        assertEquals(adventurer, treasure.getAdventurer());
        assertEquals(1, adventurer.getPickedTreasures());

        //The second adventurer try to land on the same cell
        Adventurer doe = new Adventurer("Doe",2,1, "N", "");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            treasure.land(doe);
        });
        String expectedMessage = "already occupied";
        assertTrue(exception.getMessage().contains(expectedMessage));

        //The adventurer leave the treasure
        treasure.leave();
        assertFalse(treasure.isOccupied());
        assertNull(treasure.getAdventurer());

        //No more treasure in the cell, the adventure has 2 treasures
        treasure.land(adventurer);
        assertFalse(treasure.hasTreasures());
        assertTrue(treasure.isOccupied());
        assertEquals(adventurer, treasure.getAdventurer());
        assertEquals(2, adventurer.getPickedTreasures());

        //The adventurer leave and land but there isn't a treasure left
        treasure.leave();
        treasure.land(adventurer);
        assertTrue(treasure.isOccupied());
        assertEquals(2, adventurer.getPickedTreasures());
    }
}
