package src.input.algo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import src.input.map.Map;

public class AStar
{
    private PriorityQueue<Map> open = new PriorityQueue<>(Comparator.comparingInt(Map::getCoast));

    private List<Map> closed = new ArrayList<>();

    private Map lastNode;

    public void run(Map start)
    {
        System.out.println("");

        open.add(start);

        while (!open.isEmpty())
        {
            Map current = open.remove();

            if (isFinalState(current))
            {
                lastNode = current;
                break;
            }
            closed.add(current);

            for (Map possibleMove : current.getPossibleMoves())
            {
                if (!closed.contains(possibleMove))
                {
                    open.add(possibleMove);
                }
            }
        }

        if (lastNode != null)
        {
            System.out.println();
        }
    }

    private boolean isFinalState(Map node)
    {
        return node.equals(Map.getFinalState());
    }
}
