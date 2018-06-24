import input.InputManager;
import map.PuzzleMap;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Main
{

    public static void main(String[] args)
    {
        InputManager manager = new InputManager();
        int[][] map = null;

        map = manager.getMapNumbers("/Users/omotyliu/testMap1");

        int size = manager.getMapSize();
        PuzzleUtils.printMap(map, size);
        PuzzleMap wrap = new PuzzleMap(map, size);

        TreeSet<PuzzleMap> maps = new TreeSet<>();

        maps.add(wrap);
    }
}

