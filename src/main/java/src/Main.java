package src;

import src.algo.AStar;
import src.algo.ManhattanDistanceHeuristicFunction;
import src.algo.OmotyliuHeuristicFunction;
import src.algo.SimpleHeuristicFunction;
import src.input.InputManager;
import src.map.PuzzleMap;

import java.util.TreeSet;

public class Main
{
	private static final String macOS = "/Users/omotyliu/testMap1";

	private static final String windowsOS = "C:\\Users\\Oleksandr\\Desktop\\UNIT\\testMap.txt";

	public static void main(String[] args)
    {
		InputManager manager = new InputManager();
		int[][] map = null;

		map = manager.getMapNumbers(macOS);

		// construct initial state
		PuzzleMap initialState = new PuzzleMap(map, manager.getMapSize());
		PuzzleMap.generateFinalState();
		System.out.println(initialState.isSolvable());
		initialState.setHeuristicFunction(new OmotyliuHeuristicFunction());


        AStar algo = new AStar();
		PuzzleMap res = algo.run(initialState);


		res.printParentLine();
		System.out.println(algo.getExecTime() + " miliseconds");

    }
}
