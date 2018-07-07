package src;

import src.algo.ManhattanDistanceHeuristicFunction;
import src.algo.SimpleHeuristicFunction;
import src.input.InputManager;
import src.map.PuzzleMap;

public class Main {

	private static final String macOS = "/Users/mivanov/testMap";

	private static final String windowsOS = "C:\\Max\\testMap";

	public static void main(String[] args) {
		InputManager manager = new InputManager();
		int[][] map = null;

		map = manager.getMapNumbers(windowsOS);

		// construct initial state
		PuzzleMap initialState = new PuzzleMap(map, manager.getMapSize());
		initialState.printMap();
		PuzzleMap.generateFinalState();
		System.out.println(initialState.isSolvable() + " " + initialState.isSolved());

		initialState.setHeuristicFunction(new SimpleHeuristicFunction());
		initialState.setHeuristicFunction(new ManhattanDistanceHeuristicFunction(initialState));

		System.out.println("\nget possible moves\n");

		for (PuzzleMap puzzleMap : initialState.getPossibleMoves()) {
			puzzleMap.printMap();
			System.out.println("\n");
		}

		System.out.println("\nprint state which is need to find\n");
		PuzzleMap.getFinalState().printMap();

	}
}

