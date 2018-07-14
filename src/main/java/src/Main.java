package src;

import src.algo.*;
import src.input.InputManager;
import src.map.PuzzleMap;

import java.util.TreeSet;

public class Main
{
	private static final String macOS = "/Users/mivanov/testMap1";

	private static final String windowsOS = "C:\\Users\\Oleksandr\\Desktop\\UNIT\\testMap.txt";

	public static void main(String[] args)
    {
		InputManager manager = new InputManager();
		int[][] map = null;

		map = manager.getMapNumbers(args[0]);

		// construct initial state
		PuzzleMap initialState = new PuzzleMap(map, manager.getMapSize());
		PuzzleMap.generateFinalState();
		System.out.println(initialState.isSolvable());
		if (!initialState.isSolvable())
			System.exit(0);
		initialState.setHeuristicFunction(new ManhattanDistanceHeuristicFunction());


        AStar algo = new AStar();
		PuzzleMap res = algo.run(initialState);


		res.printParentLine();
		System.out.println(algo.getExecTime() + " miliseconds");

    }
}
