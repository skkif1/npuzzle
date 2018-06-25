package src;

import src.algo.SimpleHeruisticFunction;
import src.input.InputManager;
import src.map.PuzzleMap;

public class Main
{

    private static final String macOS = "/Users/omotyliu/testMap1";

    private static final String windowsOS = "C:\\Users\\Oleksandr\\Desktop\\UNIT\\testMap.txt";

    public static void main(String[] args)
    {
        InputManager manager = new InputManager();
        int[][] map = null;

        map = manager.getMapNumbers(windowsOS);

        // construct initial state
        PuzzleMap initialState = new PuzzleMap(map, manager.getMapSize());
        initialState.printMap();

        initialState.setHeruisticFunction(new SimpleHeruisticFunction());

        System.out.println("\nget possible moves\n");

        for (PuzzleMap puzzleMap : initialState.getPossibleMoves())
        {
            puzzleMap.printMap();
            System.out.println("\n");
        }

        System.out.println("\nprint state which is need to find\n");

        PuzzleMap.generateFinalState();
        PuzzleMap.getFinalState().printMap();

    }
}

