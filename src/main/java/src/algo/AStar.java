package src.algo;

import src.map.PuzzleMap;

public class AStar {

	/*
	 *   one of the implementation of herustic function
	 * */
	IHeruisticFunction heuristicFunction;

	public AStar(IHeruisticFunction heuristicFunction) {
		this.heuristicFunction = heuristicFunction;
	}

	public void run(PuzzleMap start) {
		System.out.println("A* algo execution with " + heuristicFunction.getClass() + " heuristic function");
	}

}
