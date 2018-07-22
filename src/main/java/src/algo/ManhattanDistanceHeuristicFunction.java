package src.algo;

import javafx.util.Pair;
import src.map.PuzzleMap;

import java.util.*;

public class ManhattanDistanceHeuristicFunction implements IHeuristicFunction
{

    @Override
    public int calculateGCoast(PuzzleMap state, PuzzleMap finalState) {
        int gCoast = 0;
        Map<Integer, Pair> currentStatesMap = new HashMap<>();
        Map<Integer, Pair> finalStatesMap = new HashMap<>();
        fillCurrentAndFinalMapsOfState(state, finalState, currentStatesMap, finalStatesMap);
        for (Map.Entry<Integer, Pair> entry : currentStatesMap.entrySet()) {
            gCoast += Math.abs(((int) entry.getValue().getKey()) - ((int) finalStatesMap.get(entry.getKey()).getKey())) +
                    Math.abs(((int) entry.getValue().getValue()) - ((int) finalStatesMap.get(entry.getKey()).getValue()));
        }
        return gCoast;
    }
}
