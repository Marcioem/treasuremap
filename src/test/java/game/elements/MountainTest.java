package game.elements;

import game.adventurers.Adventurer;
import game.map.elements.Mountain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MountainTest {
    @Test
    public void creationMountainTest(){
        //Try to create a mountain in negative positions
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Mountain(-1,0);
        });
        String expectedMessage = "< 0";
        assertTrue(exception.getMessage().contains(expectedMessage));

        //Create a treasure without exceptions
        Mountain mountain = new Mountain(1, 0 );
        assertEquals("M - 1 - 0", mountain.toString());
    }

    @Test
    public void landAndLeaveMountainTest(){
        //Create a treasure without exceptions
        Mountain mountain = new Mountain(1, 0 );
        Adventurer adventurer = new Adventurer("John",2,0, "N", "AAGDAAD");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            mountain.land(adventurer);
        });
        String expectedMessage = "already occupied by a mountain";
        assertTrue(exception.getMessage().contains(expectedMessage));

        //Try to create an anonymous Adventurer
        exception = assertThrows(IllegalStateException.class, mountain::leave);
        expectedMessage = "not be possible to be on a mountain";
        assertTrue(exception.getMessage().contains(expectedMessage));

        exception = assertThrows(IllegalStateException.class, () -> {
            mountain.setAdventurer(adventurer);
        });
        expectedMessage = "cannot be on a mountain";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
