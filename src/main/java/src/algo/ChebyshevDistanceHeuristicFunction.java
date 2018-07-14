package src.algo;


import javafx.util.Pair;
import src.map.PuzzleMap;

import java.util.HashMap;
import java.util.Map;

public class ChebyshevDistanceHeuristicFunction implements IHeuristicFunction {
    @Override
    public int calculateGCoast(PuzzleMap state, PuzzleMap finalState) {
        int gCoast = 0;
        Map<Integer, Pair> currentStatesMap = new HashMap<>();
        Map<Integer, Pair> finalStatesMap = new HashMap<>();
        fillCurrentAndFinalMapsOfState(state, finalState, currentStatesMap, finalStatesMap);
        int deltaX;
        int deltaY;
        for (Map.Entry<Integer, Pair> entry : currentStatesMap.entrySet()) {
            deltaX = Math.abs(((int) entry.getValue().getKey()) - ((int) finalStatesMap.get(entry.getKey()).getKey()));
            deltaY = Math.abs(((int) entry.getValue().getValue()) - ((int) finalStatesMap.get(entry.getKey()).getValue()));
            gCoast += Math.max(deltaX, deltaY);
        }
        return gCoast;
    }
}
