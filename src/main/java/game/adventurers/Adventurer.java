package game.adventurers;

import java.util.LinkedList;
import java.util.Objects;

import static game.adventurers.Action.fromActionInitial;
import static game.adventurers.Orientation.fromOrientationInitial;

public class Adventurer {
    private final String name;
    private int xAxis;
    private int yAxis;
    private int pickedTreasures;
    private Orientation orientation;
    private LinkedList<Action> actionsList;



    public Adventurer(String name, int xAxis, int yAxis, String orientation, String actions) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("The name of an adventurer cannot be empty.");
        }
        this.name = name;
        this.orientation = fromOrientationInitial(Objects.requireNonNull(orientation));
        initializeActions(actions);
        if(xAxis < 0 || yAxis < 0){
            throw new IllegalArgumentException("The x Axis or y axis point cannot be < 0.");
        }
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.pickedTreasures = 0;
    }

    private void initializeActions(String actions){
        this.actionsList = new LinkedList<>();
        if(actions!=null){
            var chars =actions.split("");
            for (var i = 0; i < actions.length(); i++) {
                this.actionsList.offer(fromActionInitial(chars[i]));
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getxAxis() {
        return xAxis;
    }

    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    public int getPickedTreasures() {
        return pickedTreasures;
    }

    public LinkedList<Action> getActionsList() {
        return actionsList;
    }

    public Action getNextAction(){
        return actionsList.poll();
    }

    public boolean hasFinishedActions(){
        return actionsList.isEmpty();
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void loot(){
        pickedTreasures+=1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adventurer that = (Adventurer) o;
        return xAxis == that.xAxis &&
                yAxis == that.yAxis &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, xAxis, yAxis);
    }

    @Override
    public String toString(){
        return "A - " + this.name + " - " + this.xAxis + " - " + this.yAxis + " - " + this.orientation.toString() + " - " + this.pickedTreasures;
    }

}
