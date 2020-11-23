package game.adventurers;

import game.map.TreasureMap;

public enum Action {
    MOVE("A"){
        @Override
        public boolean check(Adventurer adventurer, TreasureMap treasureMap){
            return !adventurer.getOrientation().getCellAhead(adventurer, treasureMap).isOccupied();
        }
        @Override
        public void perform(Adventurer adventurer, TreasureMap treasureMap) {
            adventurer.getOrientation().move(adventurer, treasureMap);
        }
    },
    ROTATE_RIGHT("D"){
        @Override
        public void perform(Adventurer adventurer, TreasureMap treasureMap) {
            adventurer.getOrientation().rotateRight(adventurer);
        }
    },
    ROTATE_LEFT("G"){
        @Override
        public void perform(Adventurer adventurer, TreasureMap treasureMap) {
            adventurer.getOrientation().rotateLeft(adventurer);
        }
    };

    private String initial;

    Action(String initial) {
        this.initial = initial;
    }

    public static Action fromActionInitial(String text){
        for (Action o : Action.values()) {
            if (o.initial.equalsIgnoreCase(text)) {
                return o;
            }
        }
        throw new IllegalArgumentException("Unknown action in file for a adventurer.");
    }

    public boolean check(Adventurer adventurer, TreasureMap treasureMap) {
        return true;
    }

    public abstract void perform(Adventurer adventurer, TreasureMap treasureMap);
}
