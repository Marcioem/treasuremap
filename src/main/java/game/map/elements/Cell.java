package game.map.elements;

import game.adventurers.Adventurer;

import java.util.Objects;

public abstract class Cell {
    private final int xAxis;
    private final int yAxis;
    private Adventurer adventurer;
    private boolean occupied;

    public Cell(int xAxis, int yAxis) {
        if(xAxis < 0 || yAxis < 0){
            throw new IllegalArgumentException("The x Axis or y axis point cannot be < 0.");
        }
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.occupied = false;
        this.adventurer = null;
    }

    public Cell(int xAxis, int yAxis, boolean occupied) {
        if(xAxis < 0 || yAxis < 0){
            throw new IllegalArgumentException("The x Axis or y axis point cannot be < 0.");
        }
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.occupied = occupied;
        this.adventurer = null;
    }

    public int getxAxis() {
        return xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void land(Adventurer a){
        if(isOccupied()){
            throw new IllegalStateException("This cell is already occupied and an adventurer tried to land on it.");
        }
        setAdventurer(Objects.requireNonNull(a));
        setOccupied(true);
    };

    public void leave() {
        setAdventurer(null);
        setOccupied(false);
    }

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public void setAdventurer(Adventurer adventurer) {
        setOccupied(true);
        this.adventurer = adventurer;
    }
}
