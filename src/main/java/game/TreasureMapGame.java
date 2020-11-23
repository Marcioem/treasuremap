package game;


import game.map.TreasureMap;
import game.utils.GameUtils;

import java.io.IOException;


public class TreasureMapGame {

    public static void main(String[] args) throws IOException {
        //Get File for start game
        if (args.length < 1) {
            throw new IllegalArgumentException("A parameter's required : a path to a file must be specify for the initialization.");
        }

        //initialize treasure map
        TreasureMap treasureMap = GameUtils.initializeGameFromFile(args[0]);

        //run the process
        treasureMap.processGame();

        //write the results
        GameUtils.writeGameResultInFile(treasureMap);
    }
}
