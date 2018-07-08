package src.algo;

import javafx.util.Pair;
import src.map.PuzzleMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ManhattanDistanceHeuristicFunction implements IHeuristicFunction {

	PuzzleMap map;

	public ManhattanDistanceHeuristicFunction(PuzzleMap map) {
		this.map = map;
		oldStates.add(map);
		currentStates.addAll(map.getPossibleMoves());
		solve();
	}

	private List<PuzzleMap> oldStates = new ArrayList<>();
	private List<PuzzleMap> currentStates = new ArrayList<>();

	@Override
	public int calculateGCoast(PuzzleMap state, PuzzleMap finalState) {
		int gCoast = 0;
		TreeMap<Integer, Pair> currentStatesMap = new TreeMap<>();
		TreeMap<Integer, Pair> finalStatesMap = new TreeMap<>();
		for (int i = 0; i < PuzzleMap.size; i++) {
			for (int j = 0; j < PuzzleMap.size; j++) {
				currentStatesMap.put(state.getMap()[i][j], new Pair<>(i, j));
				finalStatesMap.put(finalState.getMap()[i][j], new Pair<>(i, j));
			}
		}
		for (Map.Entry<Integer, Pair> entry : currentStatesMap.entrySet()) {
			gCoast += Math.abs(((int) entry.getValue().getKey()) - ((int) finalStatesMap.get(entry.getKey()).getKey())) +
					Math.abs(((int) entry.getValue().getValue()) - ((int) finalStatesMap.get(entry.getKey()).getValue()));
		}
		return gCoast;
	}

	public void solve() {
		while (true) {
			currentStates.sort((o1, o2) -> (o1.getCoast() - o2.getCoast()));
			if (currentStates.get(0).isSolved()) break;
			currentStates.addAll(currentStates.get(0).getPossibleMoves());
			oldStates.add(currentStates.get(0));
			currentStates.remove(0);
		}
		System.out.println("SOLVED");
	}
}
