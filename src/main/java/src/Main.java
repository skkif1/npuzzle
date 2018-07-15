package src;


import src.algo.*;
import src.input.InputManager;
import src.map.PuzzleMap;

import java.util.ArrayList;
import java.util.List;

public class Main
{
	private static final String macOS = "/Users/mivanov/testMap1";

	private static final String windowsOS = "C:\\Users\\Oleksandr\\Desktop\\UNIT\\testMap.txt";

	public static void main(String[] args)
    {

		IHeuristicFunction heuristicFunction = new ManhattanDistanceHeuristicFunction();
		List<String> filePaths = new ArrayList<>();

		for (String arg : args) {
			if (arg.equalsIgnoreCase("-m")){
				heuristicFunction = new ManhattanDistanceHeuristicFunction();
			} else if (arg.equalsIgnoreCase("-e")){
				heuristicFunction = new EuclideanDistanceHeuristicFunction();
			} else if (arg.equalsIgnoreCase("-c")){
				heuristicFunction = new ChebyshevDistanceHeuristicFunction();
			} else {
				filePaths.add(arg);
			}
		}

		InputManager manager = new InputManager();
		AStar algo = new AStar();
		for (String path : filePaths) {
			solveMap(path, heuristicFunction, manager, algo);
		}
	}

    private static void solveMap(String path, IHeuristicFunction heuristicFunction, InputManager manager, AStar algo){
		int[][] map = manager.getMapNumbers(path);
		manager.setMapSize(map.length);
		PuzzleMap initialState = new PuzzleMap(map, manager.getMapSize());
		PuzzleMap.generateFinalState();
		boolean isSolvable = initialState.isSolvable();
		if (!isSolvable){
			System.out.println("The map " + path + " is unsolvable");
			return;
		}
		initialState.setHeuristicFunction(heuristicFunction);
		new Thread(() -> {
            PuzzleMap res = algo.run(initialState);
            res.printParentLine();
            System.out.println(algo.getExecTime() + " milliseconds");
        }).start();
	}

}
