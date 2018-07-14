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

//    public void solve() {
//        while (true) {
//            currentStates.sort((o1, o2) -> (o1.getCoast() - o2.getCoast()));
//            if (currentStates.get(0).isSolved()) break;
//            currentStates.addAll(currentStates.get(0).getPossibleMoves());
//            oldStates.add(currentStates.get(0));
//            currentStates.remove(0);
//        }
//        System.out.println("SOLVED");
//        PuzzleMap resultState = currentStates.get(0);
//        System.out.println();
//        List<PuzzleMap> result = new ArrayList<>();
//        while (resultState != null) {
//            result.add(0, resultState);
//            resultState = resultState.getParent();
//        }
//        System.out.println("111");
//        System.out.println();
//        for (PuzzleMap puzzleMap : result) {
//            puzzleMap.printMap();
//            System.out.println();
//        }
//        System.out.println("222");
//    }
}
