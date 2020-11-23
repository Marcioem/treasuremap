package game.map.elements;

import game.adventurers.Adventurer;

public class Mountain extends Cell {
    public Mountain(int xAxis, int yAxis) {
        super(xAxis, yAxis, true);
    }

    @Override
    public void land(Adventurer a) {
        throw new IllegalStateException("This cell is already occupied by a mountain and an adventurer cannot land on it.");
    }

    @Override
    public void leave() {
        throw new IllegalStateException("An adventurer tried to leave a mountain while it should not be possible to be on a mountain.");
    }

    @Override
    public void setAdventurer(Adventurer a) {
        throw new IllegalStateException("An adventurer cannot be on a mountain.");
    }

    @Override
    public String toString(){
        return "M - " + getxAxis() + " - " + getyAxis();
    }

}
