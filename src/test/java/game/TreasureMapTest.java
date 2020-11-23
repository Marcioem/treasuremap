package game;

import game.adventurers.Adventurer;
import game.adventurers.Orientation;
import game.map.TreasureMap;
import game.map.elements.Mountain;
import game.map.elements.Treasure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreasureMapTest {

    @Test
    public void creationTreasureMapEmptyTest(){
        //Create a map with correct dimensions without elements
        TreasureMap treasureMap = new TreasureMap(4,5);

        assertEquals("C - 4 - 5", treasureMap.toString());

        assertTrue(treasureMap.getMountainsCells().isEmpty());
        assertTrue(treasureMap.getTreasuresCells().isEmpty());
        assertTrue(treasureMap.getAdventurersStagnant().isEmpty());
        assertTrue(treasureMap.getAdventurersWithActions().isEmpty());

        //Try to create with negatives dimensions
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TreasureMap treasureMapFailure = new TreasureMap(0,-5);
        });
        String expectedMessage = "<= 0";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void AddCellsBeyondDimensions_ShouldThrowTest(){
        //Create a map with correct dimensions without elements
        TreasureMap treasureMap = new TreasureMap(2,2);
        assertEquals(2, treasureMap.getMap().length);
        assertEquals(2, treasureMap.getMap()[0].length);

        Mountain mountainInX1Y2 = new Mountain(1, 5 );
        Treasure treasure5InX3Y1 = new Treasure(3,1, 4);
        Adventurer johnWithActions = new Adventurer("John",4,5, "S", "GGGD");

        String expectedMessage = "beyond dimensions";

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            treasureMap.addAdventurer(johnWithActions);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            treasureMap.addSpecialCell(mountainInX1Y2);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            treasureMap.addSpecialCell(treasure5InX3Y1);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));

        assertTrue(treasureMap.getMountainsCells().isEmpty());
        assertTrue(treasureMap.getTreasuresCells().isEmpty());
        assertTrue(treasureMap.getAdventurersStagnant().isEmpty());
        assertTrue(treasureMap.getAdventurersWithActions().isEmpty());
    }

    @Test
    public void creationTreasureMapAndAddElementsTest(){
        /* Create the  map(5x5) :
         -      -      -      -              -
         -      -      -      T(5) & Alice   -
         -      M      John   -              -
         -      -      -      -              -
         -      -      -      -              Doe
         */
        TreasureMap treasureMap = new TreasureMap(5,5);
        Mountain mountainInX1Y2 = new Mountain(1, 2 );
        Treasure treasure5InX3Y1 = new Treasure(3,1, 5);
        Adventurer johnWithActions = new Adventurer("John",2,2, "N", "GAGGAD");
        Adventurer doeWithoutActions = new Adventurer("Doe",4,4, "N", "");
        Adventurer aliceWithoutActions = new Adventurer("Alice", treasure5InX3Y1.getxAxis(),treasure5InX3Y1.getyAxis(), "N", "");

        treasureMap.addSpecialCell(mountainInX1Y2);
        treasureMap.addSpecialCell(treasure5InX3Y1);
        treasureMap.addAdventurer(johnWithActions);
        treasureMap.addAdventurer(doeWithoutActions);
        treasureMap.addAdventurer(aliceWithoutActions);

        //Verify the map
        assertEquals(5, treasureMap.getMap().length);
        assertEquals(5, treasureMap.getMap()[0].length);
        assertEquals(1, treasureMap.getMountainsCells().size());
        assertEquals(1, treasureMap.getTreasuresCells().size());
        assertEquals(2, treasureMap.getAdventurersStagnant().size());
        assertEquals(1, treasureMap.getAdventurersWithActions().size());

        assertEquals(mountainInX1Y2, treasureMap.getCell(mountainInX1Y2.getxAxis(), mountainInX1Y2.getyAxis()));
        assertEquals(treasure5InX3Y1, treasureMap.getCell(treasure5InX3Y1.getxAxis(), treasure5InX3Y1.getyAxis()));
        assertEquals(johnWithActions, treasureMap.getCell(johnWithActions.getxAxis(), johnWithActions.getyAxis()).getAdventurer());
        assertEquals(doeWithoutActions, treasureMap.getCell(doeWithoutActions.getxAxis(), doeWithoutActions.getyAxis()).getAdventurer());
        assertEquals(aliceWithoutActions, treasureMap.getCell(aliceWithoutActions.getxAxis(), aliceWithoutActions.getyAxis()).getAdventurer());

        //Try to put elements on occupied cells
        String expectedMessage = "already occupied";

        //Treasure on a mountain cell
        Treasure treasureInMountainPos = new Treasure(mountainInX1Y2.getxAxis(),mountainInX1Y2.getyAxis(), 2);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            treasureMap.addSpecialCell(treasureInMountainPos);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));

        //Adventurer on a cell occupied by an adventurer
        Adventurer adventurerOnJohnPos = new Adventurer("JohnCopy",johnWithActions.getxAxis(),johnWithActions.getxAxis(), "N", "GGGD");
        exception = assertThrows(IllegalArgumentException.class, () -> {
            treasureMap.addAdventurer(adventurerOnJohnPos);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));

        //Adventurer on a mountain
        Adventurer adventurerOnMontain = new Adventurer("MontainDweller", mountainInX1Y2.getxAxis(),mountainInX1Y2.getyAxis(), "N", null);
        exception = assertThrows(IllegalArgumentException.class, () -> {
            treasureMap.addAdventurer(adventurerOnMontain);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void processGameTest(){
        /* Create the  map(5x5) :
         -      -      -      -              -
         -      -      -      Alice          -
         -      M      John   -              Doe
         -      -      -      -              -
         -      -      -      -              -
         */
        TreasureMap treasureMap = new TreasureMap(5,5);
        Mountain mountainInX1Y2 = new Mountain(1, 2 );
        Treasure treasure2InX4Y1 = new Treasure(4,1, 2);

        //John actions are rotateLeft, advance, rotateLeft *2, Advance, rotateRight
        Adventurer john = new Adventurer("John",2,2, "N", "GAGGAD");
        //Doe actions are advance ( orientation North)
        Adventurer doe = new Adventurer("Doe",4,2, "N", "A");
        //Alice actions are advance ( orientation East)
        Adventurer alice = new Adventurer("Alice", 3,1, "E", "A");

        treasureMap.addSpecialCell(mountainInX1Y2);
        treasureMap.addSpecialCell(treasure2InX4Y1);
        treasureMap.addAdventurer(john);
        treasureMap.addAdventurer(doe);
        treasureMap.addAdventurer(alice);

        //Alice And Doe will try to go to Cell(4,1)
        //Doe is first, so doe will move and Alice will stay
        //John rotate to left (east) will try to go to mountain but will fail and will rotate to west ( left *2 )and will move
        //Then will rotate right (orientation south )
        treasureMap.processGame();

         /* Expected result after process :
         -      -      -      -              -
         -      -      -      Alice         T(1) & Doe
         -      M      -      -              -
         -      -      John   -              -
         -      -      -      -              -
         */
        assertEquals(mountainInX1Y2, treasureMap.getCell(mountainInX1Y2.getxAxis(), mountainInX1Y2.getyAxis()));
        assertEquals(treasure2InX4Y1, treasureMap.getCell(treasure2InX4Y1.getxAxis(), treasure2InX4Y1.getyAxis()));

        assertEquals(Orientation.SOUTH, john.getOrientation());
        assertEquals(1, doe.getPickedTreasures());
        assertEquals(0, alice.getPickedTreasures());

        assertEquals("A - John - 3 - 2 - S - 0", john.toString());
        assertEquals("A - Doe - 4 - 1 - N - 1", doe.toString());
        assertEquals("A - Alice - 3 - 1 - E - 0", alice.toString());
    }
}
