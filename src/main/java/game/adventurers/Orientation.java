package game.adventurers;

import game.map.TreasureMap;
import game.map.elements.Cell;

public enum Orientation {
    NORTH("N"){
        @Override
        public void rotateRight(Adventurer adventurer) {
            adventurer.setOrientation(Orientation.EAST);
        }

        @Override
        public void rotateLeft(Adventurer adventurer) {
            adventurer.setOrientation(Orientation.WEST);
        }

        @Override
        public Cell getCellAhead(Adventurer adventurer, TreasureMap treasureMap) {
            return treasureMap.getCell(adventurer.getxAxis(), adventurer.getyAxis()-1);
        }

        @Override
        public void move(Adventurer adventurer, TreasureMap treasureMap) {
            treasureMap.getCell(adventurer.getxAxis(), adventurer.getyAxis()).leave();
            getCellAhead(adventurer, treasureMap).land(adventurer);
            adventurer.setyAxis(adventurer.getyAxis()-1);
        }
    },
    SOUTH("S"){
        @Override
        public void rotateRight(Adventurer adventurer) {
            adventurer.setOrientation(Orientation.WEST);
        }

        @Override
        public void rotateLeft(Adventurer adventurer) {
            adventurer.setOrientation(Orientation.EAST);
        }

        @Override
        public Cell getCellAhead(Adventurer adventurer, TreasureMap treasureMap) {
            return treasureMap.getCell(adventurer.getxAxis(), adventurer.getyAxis()+1);
        }

        @Override
        public void move(Adventurer adventurer, TreasureMap treasureMap) {
            treasureMap.getCell(adventurer.getxAxis(), adventurer.getyAxis()).leave();
            getCellAhead(adventurer, treasureMap).land(adventurer);
            adventurer.setyAxis(adventurer.getyAxis()+1);
        }
    },
    WEST("O"){
        @Override
        public void rotateRight(Adventurer adventurer) {
            adventurer.setOrientation(Orientation.NORTH);
        }

        @Override
        public void rotateLeft(Adventurer adventurer) {
            adventurer.setOrientation(Orientation.SOUTH);
        }

        @Override
        public Cell getCellAhead(Adventurer adventurer, TreasureMap treasureMap) {
            return treasureMap.getCell(adventurer.getxAxis()-1, adventurer.getyAxis());
        }

        @Override
        public void move(Adventurer adventurer, TreasureMap treasureMap) {
            treasureMap.getCell(adventurer.getxAxis(), adventurer.getyAxis()).leave();
            getCellAhead(adventurer, treasureMap).land(adventurer);
            adventurer.setxAxis(adventurer.getxAxis()-1);
        }
    },
    EAST("E"){
        @Override
        public void rotateRight(Adventurer adventurer) {
            adventurer.setOrientation(Orientation.SOUTH);
        }

        @Override
        public void rotateLeft(Adventurer adventurer) {
            adventurer.setOrientation(Orientation.NORTH);
        }

        @Override
        public Cell getCellAhead(Adventurer adventurer, TreasureMap treasureMap) {
            return treasureMap.getCell(adventurer.getxAxis()+1, adventurer.getyAxis());
        }

        @Override
        public void move(Adventurer adventurer, TreasureMap treasureMap) {
            treasureMap.getCell(adventurer.getxAxis(), adventurer.getyAxis()).leave();
            getCellAhead(adventurer, treasureMap).land(adventurer);
            adventurer.setxAxis(adventurer.getxAxis()+1);
        }
    };

    private String initial;

    Orientation(String initial) {
        this.initial = initial;
    }

    public abstract void rotateRight(Adventurer adventurer);
    public abstract void rotateLeft(Adventurer adventurer);
    public abstract void move(Adventurer adventurer, TreasureMap treasureMap);
    public abstract Cell getCellAhead(Adventurer adventurer, TreasureMap treasureMap);

    public String getInitial() {
        return this.initial;
    }

    @Override
    public String toString(){
        return this.initial;
    }

    public static Orientation fromOrientationInitial(String text) {
        for (Orientation o : Orientation.values()) {
            if (o.initial.equalsIgnoreCase(text)) {
                return o;
            }
        }
        throw new IllegalArgumentException("Unknown orientation in file for a adventurer.");
    }
}
