package src.algo;

import javafx.util.Pair;
import src.map.PuzzleMap;

import java.util.*;

public class ManhattanDistanceHeuristicFunction implements IHeuristicFunction
{

    String name = "ManhattanDistanceHeuristicFunction";

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

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ManhattanDistanceHeuristicFunction that = (ManhattanDistanceHeuristicFunction) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
