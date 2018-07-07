package src.algo;

import src.map.PuzzleMap;

public class AStar {

	/*
	 *   one of the implementation of heuristic function
	 * */
	IHeuristicFunction heuristicFunction;

	public AStar(IHeuristicFunction heuristicFunction) {
		this.heuristicFunction = heuristicFunction;
	}

	public void run(PuzzleMap start) {
		System.out.println("A* algo execution with " + heuristicFunction.getClass() + " heuristic function");
	}

}
