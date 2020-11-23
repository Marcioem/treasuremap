package game.utils;

import game.adventurers.Adventurer;
import game.map.TreasureMap;
import game.map.elements.Mountain;
import game.map.elements.Treasure;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class GameUtils {

    public static TreasureMap initializeGameFromFile(String fileName) throws IOException {
        int currentLineNumber = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            //Creation of the treasure map
            TreasureMap treasureMap = null;
            boolean mapCreated = false;
            String line;
            while (!mapCreated && (line = reader.readLine()) != null) {
                currentLineNumber++;
                String[] splitLine = line.replaceAll("\\s+","").split("-");
                if(splitLine[0].equals("C")){
                    //parse Int can failed
                    treasureMap = new TreasureMap(parseInt(splitLine[1]), parseInt(splitLine[2]));
                    mapCreated = true;
                }
            }
            if(treasureMap==null){
                throw new IllegalArgumentException("File " + fileName + " do not declare a treasure map in it.\n" +
                        "A treasure map is declared like 'C - {Cells number in width} - {Cells number in height}'.");
            }
            while ((line = reader.readLine()) != null) {
                currentLineNumber++;
                //Initialize elements of the treasure map
                String lineWithoutSpace = line.replaceAll("\\s+","");
                char firstChar = lineWithoutSpace.charAt(0);
                String[] splitLine = lineWithoutSpace.split("-");
                switch (firstChar){
                    case '#':
                        break;
                    case 'M':
                        treasureMap.addSpecialCell(new Mountain(parseInt(splitLine[1]), parseInt(splitLine[2])));
                        break;
                    case 'T':
                        treasureMap.addSpecialCell(new Treasure(parseInt(splitLine[1]), parseInt(splitLine[2]), parseInt(splitLine[3])));
                        break;
                    case 'A':
                        initializeAdventurerAndAddToMap(splitLine, treasureMap);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown element in file.");
                }
            }
            //treasureMap.initializeEmptyCase();
            return treasureMap;
        }catch (NumberFormatException  e) {
            throw new NumberFormatException("Position for an element in the map is not a number. Please verify the file content at line " + currentLineNumber);
        }catch (IllegalArgumentException  e) {
            throw new IllegalArgumentException("Error in file " + fileName + " at line " + currentLineNumber + ".\n" + e.getMessage());
        }catch (FileNotFoundException e){
            throw new FileNotFoundException("File " + fileName + " not found. Please verify that the file exist or specify another.");
        }
    }

    public static void initializeAdventurerAndAddToMap(String[] splitLine, TreasureMap treasureMap) {
        String actions = splitLine.length<=5?"":splitLine[5];
        Adventurer a = new Adventurer(splitLine[1], parseInt(splitLine[2]),  parseInt(splitLine[3]), splitLine[4], actions);
        treasureMap.addAdventurer(a);
    }

    public static void writeGameResultInFile(TreasureMap treasureMap) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH-mm").format(new Date());
        String resultfileName = "treasure-map-results-"+date+".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultfileName))) {
            writer.write(treasureMap.toString() +  System.getProperty("line.separator"));
            for(var mountain : treasureMap.getMountainsCells()){
                writer.write(mountain.toString() +  System.getProperty("line.separator"));
            }
            for(var treasure : treasureMap.getTreasuresCells()){
                if(treasure.hasTreasures()){
                    writer.write(treasure.toString() +  System.getProperty("line.separator"));
                }
            }
            for(var adventurer : treasureMap.getAdventurersStagnant()){
                writer.write(adventurer.toString() + System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            e.printStackTrace();
        }

    }
}
