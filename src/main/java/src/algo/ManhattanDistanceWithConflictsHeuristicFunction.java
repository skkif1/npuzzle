package src.algo;

import javafx.util.Pair;
import src.map.PuzzleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManhattanDistanceWithConflictsHeuristicFunction implements IHeuristicFunction
{

    List<Map.Entry<Integer, Pair>> valueValuePlace = new ArrayList<>();


    @Override
    public int calculateGCoast(PuzzleMap state, PuzzleMap finalState) {
        int gCoast = 0;
        Map<Integer, Pair> currentStatesMap = new HashMap<>();
        Map<Integer, Pair> finalStatesMap = new HashMap<>();
        fillCurrentAndFinalMapsOfState(state, finalState, currentStatesMap, finalStatesMap);
        for (Map.Entry<Integer, Pair> entry : currentStatesMap.entrySet()) {
            int coast = Math.abs(((int) entry.getValue().getKey()) - ((int) finalStatesMap.get(entry.getKey()).getKey())) +
                    Math.abs(((int) entry.getValue().getValue()) - ((int) finalStatesMap.get(entry.getKey()).getValue()));

            if (coast == 1)
            {
                valueValuePlace.add(entry);
            }
            gCoast += coast;
        }
        getConflicts();
        return gCoast;
    }

    public int getConflicts()
    {

        for (Map.Entry<Integer, Pair> integerPairEntry : valueValuePlace)
        {
            if (integerPairEntry.getKey() == 0)
                continue;
            int finalI =
            int finalJ =
        }
        return 0;
    }
}
