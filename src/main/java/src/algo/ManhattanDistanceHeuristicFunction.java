package src.algo;

import src.map.PuzzleMap;

import java.util.ArrayList;
import java.util.List;

public class ManhattanDistanceHeuristicFunction implements IHeuristicFunction {

	PuzzleMap map;

	public ManhattanDistanceHeuristicFunction(PuzzleMap map) {
		this.map = map;
		oldStates.add(map);
		currentStates.addAll(map.getPossibleMoves());
	}

	private List<PuzzleMap> oldStates = new ArrayList<>();
	private List<PuzzleMap> currentStates = new ArrayList<>();

	@Override
	public int calculateGCoast(PuzzleMap state, PuzzleMap finalState) {
		int gCoast = 0;
		for (int i = 0; i < PuzzleMap.size; i++) {
			for (int j = 0; j < PuzzleMap.size; j++) {

			}
		}
		return 0;
	}

	public void solve() {
		while (true) {
			currentStates.sort((o1, o2) -> 0);
		}
	}
}
