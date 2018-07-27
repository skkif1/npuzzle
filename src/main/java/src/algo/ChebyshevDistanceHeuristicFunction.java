package src.algo;


import javafx.util.Pair;
import src.map.PuzzleMap;

import java.util.HashMap;
import java.util.Map;

public class ChebyshevDistanceHeuristicFunction implements IHeuristicFunction {

    String name = "ChebyshevDistanceHeuristicFunction";

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

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChebyshevDistanceHeuristicFunction that = (ChebyshevDistanceHeuristicFunction) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
