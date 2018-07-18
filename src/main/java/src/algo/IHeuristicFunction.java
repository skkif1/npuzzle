package src.algo;

import src.map.PuzzleMap;

public interface IHeuristicFunction {

	/*
	 *  return the G value (heuristic result)
	 * */
	int calculateGCoast(PuzzleMap state, PuzzleMap finalState);
}
