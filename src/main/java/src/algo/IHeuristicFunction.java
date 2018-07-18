package src.algo;

import javafx.util.Pair;
import src.map.PuzzleMap;
import java.util.Map;

public interface IHeuristicFunction {

	/*
	 *  return the G value (heuristic result)
	 * */
	int calculateGCoast(PuzzleMap state, PuzzleMap finalState);

	default void fillCurrentAndFinalMapsOfState(PuzzleMap state, PuzzleMap finalState,
												Map<Integer, Pair> currentStatesMap, Map<Integer, Pair> finalStatesMap){
		for (int i = 0; i < state.size; i++) {
			for (int j = 0; j < state.size; j++) {
				currentStatesMap.put(state.getMap()[i][j], new Pair<>(i, j));
				finalStatesMap.put(finalState.getMap()[i][j], new Pair<>(i, j));
			}
		}
	}
}
