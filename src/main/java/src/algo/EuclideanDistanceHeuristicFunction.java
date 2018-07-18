package src.algo;


import javafx.util.Pair;
import src.map.PuzzleMap;

import java.util.HashMap;
import java.util.Map;

public class EuclideanDistanceHeuristicFunction implements IHeuristicFunction {

    @Override
    public int calculateGCoast(PuzzleMap state, PuzzleMap finalState) {
        int gCoast = 0;
        Map<Integer, Pair> currentStatesMap = new HashMap<>();
        Map<Integer, Pair> finalStatesMap = new HashMap<>();
        fillCurrentAndFinalMapsOfState(state, finalState, currentStatesMap, finalStatesMap);
        for (Map.Entry<Integer, Pair> entry : currentStatesMap.entrySet()) {
            gCoast += Math.sqrt(Math.pow(((int) entry.getValue().getKey()) - ((int) finalStatesMap.get(entry.getKey()).getKey()), 2) +
                    Math.pow(((int) entry.getValue().getValue()) - ((int) finalStatesMap.get(entry.getKey()).getValue()), 2));
        }
        return gCoast;
    }
}
