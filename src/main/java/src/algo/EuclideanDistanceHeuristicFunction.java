package src.algo;


import javafx.util.Pair;
import src.map.PuzzleMap;

import java.util.HashMap;
import java.util.Map;

public class EuclideanDistanceHeuristicFunction implements IHeuristicFunction {

    String name = "EuclideanDistanceHeuristicFunction";

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EuclideanDistanceHeuristicFunction that = (EuclideanDistanceHeuristicFunction) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
