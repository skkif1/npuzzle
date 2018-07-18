package src;

import src.algo.AStar;
import src.algo.ManhattanDistanceHeuristicFunction;
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
		initialState.setHeuristicFunction(new ManhattanDistanceHeuristicFunction());


        AStar algo = new AStar();
		PuzzleMap res = algo.run(initialState);


		res.printParentLine();
		System.out.println(algo.getExecTime() + " seconds");



//        SortedPuzzleSet test = new SortedPuzzleSet();
//        PuzzleMap mapClone = new PuzzleMap(map, manager.getMapSize());
//        mapClone.setHeuristicFunction(new ManhattanDistanceHeuristicFunction());
//
//
//        test.add(initialState);
//        System.out.println(test.size() == 1);
//        test.add(initialState);
//        System.out.println(test.size() == 1);
//        test.pollFirst();
//        System.out.println(test.size() == 0);
//        test.add(initialState);
//        System.out.println(test.contains(initialState));
//		test.add(mapClone);
//		System.out.println(test.get(initialState).getCoast());
//        System.out.println(test.contains(mapClone));
//        System.out.println(test.get(mapClone).getCoast());


    }
}
