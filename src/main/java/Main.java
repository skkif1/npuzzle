import input.InputManager;

import java.util.List;

public class Main
{

    public static void main(String[] args)
    {
        InputManager manager = new InputManager();
        int[][] map = null;

        map = manager.getMapNumbers("/Users/omotyliu/testMap1");

        int size = manager.getMapSize();
        PuzzleUtils.printMap(map, size);
    }
}

