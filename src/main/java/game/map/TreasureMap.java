package game.map;

import game.adventurers.Adventurer;
import game.map.elements.Cell;
import game.map.elements.CellCommon;
import game.map.elements.Mountain;
import game.map.elements.Treasure;

import java.util.LinkedList;

public class TreasureMap {
    private final LinkedList<Adventurer> adventurersWithActions;
    private final LinkedList<Adventurer> adventurersStagnant;
    private final LinkedList<Treasure> treasuresCells;
    private final LinkedList<Mountain> mountainsCells;
    private final Cell[][] map;

    public TreasureMap(int xAxisMax, int yAxisMax) {
        if(xAxisMax <= 0 || yAxisMax <= 0){
            throw new IllegalArgumentException("The treasure map width and height cannot be <= 0.");
        }
        this.map = new Cell[xAxisMax][yAxisMax];
        for ( int i = 0; i<xAxisMax;i++){
            for (int j = 0; j<yAxisMax; j++){
                map[i][j] = new CellCommon(i, j);
            }
        }
        this.adventurersWithActions = new LinkedList<>();
        this.adventurersStagnant = new LinkedList<>();
        this.treasuresCells = new LinkedList<>();
        this.mountainsCells = new LinkedList<>();
    }

    private void verifyOccupationCell(int xAxis, int yAxis){
        if(xAxis<0 || xAxis>=map.length || yAxis<0 || yAxis>=map[0].length){
            throw new IndexOutOfBoundsException("The cell searched is beyond dimensions of the map.");
        }else if(map[xAxis][yAxis].isOccupied()){
            throw new IllegalArgumentException("The cell is already occupied by an adventure or a mountain.");
        }
    }

    private void verifyAndAddCell(Cell specialCell){
        int xAxis = specialCell.getxAxis();
        int yAxis = specialCell.getyAxis();
        verifyOccupationCell(xAxis, yAxis);
        map[xAxis][yAxis] = specialCell;
    }

    public void addSpecialCell(Treasure treasureCell){
        verifyAndAddCell(treasureCell);
        this.treasuresCells.offer(treasureCell);
    }

    public void addSpecialCell(Mountain mountainCell){
        verifyAndAddCell(mountainCell);
        this.mountainsCells.offer(mountainCell);
    }

    public void addAdventurer(Adventurer adventurer){
        int xAxis = adventurer.getxAxis();
        int yAxis = adventurer.getyAxis();
        verifyOccupationCell(xAxis, yAxis);
        map[xAxis][yAxis].setAdventurer(adventurer);
        if(!adventurer.hasFinishedActions()){
            adventurersWithActions.offer(adventurer);
        }else{
            adventurersStagnant.offer(adventurer);
        }
    }

    public Cell getCell(int xAxis, int yAxis){
        if(xAxis<0 || xAxis>=map.length || yAxis<0 || yAxis>=map[0].length){
            throw new IndexOutOfBoundsException("The cell searched is beyond the map.");
        }
        return map[xAxis][yAxis];
    }

    public void processGame() {
        while(!adventurersWithActions.isEmpty()){
            //Get next adventurer
            var currentAdventurer = adventurersWithActions.poll();
            var action = currentAdventurer.getNextAction();
            //Check if is nextAction is feasible and perform it
            if(action.check(currentAdventurer, this)){
                action.perform(currentAdventurer, this);
            }
            //Replace the adventurer at the end of the list if he doesn't finished his actions
            if(!currentAdventurer.hasFinishedActions()){
                adventurersWithActions.offer(currentAdventurer);
            }else{
                adventurersStagnant.offer(currentAdventurer);
            }
        }
    }

    public LinkedList<Adventurer> getAdventurersWithActions() {
        return adventurersWithActions;
    }

    public LinkedList<Adventurer> getAdventurersStagnant() {
        return adventurersStagnant;
    }

    public LinkedList<Treasure> getTreasuresCells() {
        return treasuresCells;
    }

    public LinkedList<Mountain> getMountainsCells() {
        return mountainsCells;
    }

    public Cell[][] getMap() {
        return map;
    }

    @Override
    public String toString(){
        return "C - " + map.length + " - " + map[0].length;
    }
}
