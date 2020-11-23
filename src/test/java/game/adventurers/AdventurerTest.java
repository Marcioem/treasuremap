package game.adventurers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdventurerTest {

    @Test
    public void givenCorrectParameters_shouldCreate(){
        //Adventurer with name, correct axis and orientation and actions.
        Adventurer adventurer = new Adventurer("John",0,1, "S", "AAGDAAD");
        assertEquals("John", adventurer.getName());
        assertEquals(0, adventurer.getxAxis());
        assertEquals(1, adventurer.getyAxis());
        assertEquals(Orientation.SOUTH, adventurer.getOrientation());
        assertEquals("AAGDAAD".length(), adventurer.getActionsList().size());

        //Adventurer with name, correct axis and orientation and null actions.
        Adventurer adventurer2 = new Adventurer("JohnDoe",8,50, "O", null);
        assertEquals(Orientation.WEST, adventurer2.getOrientation());
        assertEquals(0, adventurer2.getActionsList().size());

        //Adventurer with name, correct axis and orientation and empty actions.
        Adventurer adventurer3 = new Adventurer("JTheOne",1,2, "N", "");
        assertEquals(Orientation.NORTH, adventurer3.getOrientation());
        assertEquals(0, adventurer3.getActionsList().size());

        assertEquals("A - JTheOne - 1 - 2 - N - 0", adventurer3.toString());


    }

    @Test
    public void givenWrongParameters_shouldThrow(){
        //Try to create an anonymous Adventurer
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Adventurer(null,0,1, "S", "");
        });
        String expectedMessage = "cannot be empty";
        assertTrue(exception.getMessage().contains(expectedMessage));

        //Try to create an Adventurer with negative position
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Adventurer("ezr",0,-1, "E", "");
        });
        expectedMessage = "cannot be < 0";
        assertTrue(exception.getMessage().contains(expectedMessage));

        //Try to create an Adventurer with unknown orientation
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Adventurer("ezr",0,1, "Z", "");
        });
        expectedMessage = "Unknown orientation";
        assertTrue(exception.getMessage().contains(expectedMessage));

        //Try to create an Adventurer with unknown actions
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Adventurer("ezr",0,1, "N", "AAQ");
        });
        expectedMessage = "Unknown action";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
