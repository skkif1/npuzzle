package src;

import src.input.InputManager;

public class Main
{

    private static final String macOS = "/Users/omotyliu/testMap1";

    private static final String winbdowsOS = "C:\\Users\\Oleksandr\\Desktop\\UNIT\\testMap.txt";

    public static void main(String[] args)
    {
        InputManager manager = new InputManager();
        int[][] map = null;

        map = manager.getMapNumbers(winbdowsOS);

        int size = manager.getMapSize();
        PuzzleUtils.printMap(map, size);
    }
}

