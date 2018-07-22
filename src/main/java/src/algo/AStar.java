package src.algo;

import javafx.util.Pair;
import src.SortedPuzzleSet;
import src.StatHolder;
import src.map.PuzzleMap;

import java.util.List;
import java.util.concurrent.Callable;

public class AStar implements Callable {

    private PuzzleMap finalState;

    private PuzzleMap start;

    private SortedPuzzleSet open;

    private SortedPuzzleSet closed;

	private StatHolder holder;

	private String filePath;

	public void setInitialMap(PuzzleMap start, String filePath)
    {
        this.start = start;
        this.holder = new StatHolder(filePath);
        this.open = new SortedPuzzleSet(filePath);
        this.closed = new SortedPuzzleSet(filePath);
    	this.filePath = filePath;
    }


    @Override
    public Object call() throws Exception {

		return run();
	}

    private Pair<PuzzleMap, String> run()
    {
		holder.startTime("A* execution time");
        finalState = start.getFinalState();
        open.add(start);
        while (!open.isEmpty())
        {
            PuzzleMap currentNode = open.pollFirst();

            if (currentNode.equals(finalState))
            {
				holder.endTime("A* execution time");
            	return new Pair<>(currentNode, filePath);
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
            holder.iterate("watched node from open list");
        }
		holder.endTime("A* execution time");
		return null;
    }
}
