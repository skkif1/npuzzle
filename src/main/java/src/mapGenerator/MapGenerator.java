package src.mapGenerator;

import org.apache.commons.lang3.StringUtils;
import src.input.InputManagerException;
import src.map.PuzzleMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MapGenerator
{

    private static String baseDir = System.getProperty("user.dir");

    private static String separator = File.separator;

    private static String generatedMaps = "generatedMaps";

    private final static String tempFile = "startNode";

    protected static List<PuzzleMap> open = new ArrayList<>();

    protected static HashMap<Integer, PuzzleMap> res = new HashMap<>();


    private static PuzzleMap createStartNode(int size)
    {
        int temp [][] = new int[size][size];

        int k = 0;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                temp[i][j] = k++;
            }
        }

        PuzzleMap map = new PuzzleMap(temp, size);

        map.generateFinalState();
        return map.getFinalState();
    }

    public static List<String> generateMaps(int size, int depth) throws IOException {

        if (size > 100 || depth > 1000)
            throw new InputManagerException("Sorry seems it does not have any sense ))) ");
        PuzzleMap finalState = createStartNode(size);
        open.add(finalState);
        generateMaps(depth);

        PuzzleMap toFile = res.get(Collections.max(res.keySet()));
        writeMapToFile(toFile);

        return null;
    }

    public static void main(String[] args) throws IOException {
        generateMaps(3, 19);
    }


    private static void generateMaps(int depth)
    {
        while(!open.isEmpty())
        {
            List<PuzzleMap> moves = open.get(0).getPossibleMoves();

            for (PuzzleMap move : moves)
            {
                if (open.contains(move))
                    continue;
                open.add(move);
                res.put(move.getH(), move);
                if (move.getH() >= depth)
                    return;
            }
            open.remove(0);
        }
    }

    private static String writeMapToFile(PuzzleMap map) throws IOException {
        File sourceDir = new File(baseDir + separator + generatedMaps);
        if (!sourceDir.exists())
        {
            sourceDir.mkdir();
        }

        File mapFile = new File(sourceDir + separator + "genMap" + map.size + "x" + map.size + "depth" + map.getH());
        mapFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(mapFile));
        writer.write(map.size + "\n");
        int toWrite [][] = map.getMap();

        for (int i = 0; i < map.size; i++)
        {
            for (int j = 0; j < map.size; j++)
            {
                writer.write(toWrite[i][j] + " ");
            }
            writer.newLine();
        }
        writer.flush();
        writer.close();

        return mapFile.getName();
    }
}
