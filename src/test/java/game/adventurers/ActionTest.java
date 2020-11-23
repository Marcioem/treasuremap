package game.adventurers;

import game.map.TreasureMap;
import game.map.elements.Cell;
import game.map.elements.Mountain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ActionTest {
    private TreasureMap treasureMap;
    private Adventurer adventurer;

    @BeforeEach
    void init(){
        this.treasureMap = new TreasureMap(3,3);
        this.adventurer = new Adventurer("Cobaye", 1, 1, "N", "");
        treasureMap.addAdventurer(adventurer);
    }

    @Test
    public void adventurerRotateAndMoveInEmptyCell_ShouldSuccess(){
        //Verify the state of the cells
        assertEquals(Orientation.NORTH, this.adventurer.getOrientation());
        Cell beforeMoveCell = treasureMap.getCell(1,1);
        Cell afterMoveCell = treasureMap.getCell(1,0);

        assertFalse(afterMoveCell.isOccupied());
        assertTrue(beforeMoveCell.isOccupied());
        assertEquals(beforeMoveCell.getAdventurer(), this.adventurer);
        assertEquals( Orientation.NORTH, this.adventurer.getOrientation());

        //We move the adventurer to the north ( orientation of the adventurer )
        Action.MOVE.perform(adventurer, treasureMap);
        assertTrue(afterMoveCell.isOccupied());
        assertEquals(afterMoveCell.getAdventurer(), this.adventurer);
        assertFalse(beforeMoveCell.isOccupied());

        //Rotate to the Right ( orientation east )
        Action.ROTATE_RIGHT.perform(adventurer, treasureMap);
        assertEquals(Orientation.EAST, this.adventurer.getOrientation());

        beforeMoveCell = afterMoveCell;
        afterMoveCell = treasureMap.getCell(2,0);
        assertFalse(afterMoveCell.isOccupied());

        //Perfom move and verify cells
        Action.MOVE.perform(adventurer, treasureMap);
        assertTrue(afterMoveCell.isOccupied());
        assertEquals(afterMoveCell.getAdventurer(), this.adventurer);
        assertFalse(beforeMoveCell.isOccupied());

        //Rotate to the Right x3 ( orientation south )
        Action.ROTATE_LEFT.perform(adventurer, treasureMap);
        Action.ROTATE_LEFT.perform(adventurer, treasureMap);
        Action.ROTATE_LEFT.perform(adventurer, treasureMap);
        assertEquals(Orientation.SOUTH, this.adventurer.getOrientation());

        beforeMoveCell = afterMoveCell;
        afterMoveCell = treasureMap.getCell(2,1);

        //Perfom move and verify cells
        Action.MOVE.perform(adventurer, treasureMap);
        assertTrue(afterMoveCell.isOccupied());
        assertEquals(afterMoveCell.getAdventurer(), this.adventurer);
        assertFalse(beforeMoveCell.isOccupied());

        //Rotate to the Right ( orientation west )
        Action.ROTATE_RIGHT.perform(adventurer, treasureMap);
        assertEquals(Orientation.WEST, this.adventurer.getOrientation());
        beforeMoveCell = afterMoveCell;
        afterMoveCell = treasureMap.getCell(1,1);
        assertFalse(afterMoveCell.isOccupied());

        //Perfom move and verify cells
        Action.MOVE.perform(adventurer, treasureMap);
        assertTrue(afterMoveCell.isOccupied());
        assertEquals(afterMoveCell.getAdventurer(), this.adventurer);
        assertFalse(beforeMoveCell.isOccupied());
    }

    @Test
    public void adventurerX1Y1RotateAndMoveInOccupiedTest_ShouldStay(){
        String expectedMessage = "already occupied";

        //Place an adventurer in the northern cell
        Adventurer adventurerInX1Y0 = new Adventurer("X1Y0", 1, 0 , "N", "");
        treasureMap.addAdventurer(adventurerInX1Y0);

        //Move to the X=1 Y=0 cell where the newest adventurer is positioned
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            Action.MOVE.perform(adventurer, treasureMap);
        });
        //Assert the message error and the position of the adventurer remain the same
        assertTrue(exception.getMessage().contains(expectedMessage));
        assertEquals(this.adventurer.getxAxis(), 1);
        assertEquals(this.adventurer.getyAxis(), 1);


        //Place an mountain in the southern cell
        Mountain mountainInX1Y2 = new Mountain(1, 2 );
        treasureMap.addSpecialCell(mountainInX1Y2);

        //Move to the X=1 Y=2 cell where the newest adventurer is positioned
        Action.ROTATE_LEFT.perform(adventurer, treasureMap);
        Action.ROTATE_LEFT.perform(adventurer, treasureMap);
        assertEquals(Orientation.SOUTH, this.adventurer.getOrientation());
        exception = assertThrows(IllegalStateException.class, () -> {
            Action.MOVE.perform(adventurer, treasureMap);
        });
        //Assert the message error and the position of the adventurer remain the same
        assertTrue(exception.getMessage().contains(expectedMessage));
        assertEquals(this.adventurer.getxAxis(), 1);
        assertEquals(this.adventurer.getyAxis(), 1);

    }

    @Test
    public void adventurerRotateAndMoveOutOfMap_ShouldThrow(){
        //Move to the X=1 Y=0 cell
        Action.MOVE.perform(adventurer, treasureMap);
        //Try to go to the X=1 y=-1 cells ( doesn't exist )
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            Action.MOVE.perform(adventurer, treasureMap);
        });
        String expectedMessage = "beyond the map";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
    }
