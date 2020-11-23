package game.map.elements;

import game.adventurers.Adventurer;

import java.util.Objects;

public class Treasure extends Cell {
    private int numberTreasures;

    public Treasure(int xAxis, int yAxis, int treasures) {
        super(xAxis, yAxis);
        if(treasures <= 0){
            throw new IllegalArgumentException("The number of treasures for a treasure cell cannot be <= 0.");
        }
        this.numberTreasures = treasures;
    }

    public boolean hasTreasures(){
        return this.numberTreasures>0;
    }

    @Override
    public void land(Adventurer a){
        if(isOccupied()){
            throw new IllegalStateException("This cell is already occupied and an adventurer tried to land on it.");
        }
        setAdventurer(Objects.requireNonNull(a));
        setOccupied(true);
        if(numberTreasures>0){
            a.loot();
            numberTreasures-=1;
        }
    }

    @Override
    public String toString(){
        return "T - " + getxAxis() + " - " + getyAxis() + " - " + numberTreasures;
    }
}