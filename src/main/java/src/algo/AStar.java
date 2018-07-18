package src.algo;

import src.SortedPuzzleSet;
import src.map.PuzzleMap;

import java.util.List;
import java.util.concurrent.Callable;

public class AStar implements Callable {

    private PuzzleMap finalState;

    private PuzzleMap start;

    private PuzzleMap res;

    private SortedPuzzleSet open = new SortedPuzzleSet();

    private SortedPuzzleSet closed = new SortedPuzzleSet();

    public void setInitialMap(PuzzleMap start)
    {
        this.start = start;
    }

    @Override
    public Object call() throws Exception {
        return run();
    }

    private PuzzleMap run() {
        finalState = start.getFinalState();
        open.add(start);
        while (!open.isEmpty()) {
            PuzzleMap currentNode = open.pollFirst();

            if (currentNode.equals(finalState)) {
                return currentNode;
            }

            List<PuzzleMap> childNodes = currentNode.getPossibleMoves();

            for (PuzzleMap childNode : childNodes)
            {
                PuzzleMap temp;

                if (open.contains(childNode)) {
                    temp = open.get(childNode);
                    if (temp.compareTo(childNode) <= 0)
                        continue;
                }

                if (closed.contains(childNode)) {
                    temp = closed.get(childNode);

                    if (temp.compareTo(childNode) <= 0)
                        continue;
                }
                open.add(childNode);
            }
            closed.add(currentNode);
        }
        return null;
    }
}
