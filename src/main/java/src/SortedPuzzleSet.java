package src;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import src.map.PuzzleMap;

public class SortedPuzzleSet
{
    private HashMap<PuzzleMap, PuzzleMap> hash  = new HashMap<>();

    private Map<Integer, List<PuzzleMap>> complexityMap = new HashMap<>();

    private SortedSet<Integer> order = new TreeSet<>();

    private long size = 0;

    private StatHolder statHolder;

    public SortedPuzzleSet(String filePath)
    {
        statHolder = new StatHolder(filePath);
    }

    public void add(PuzzleMap node)
    {
        statHolder.startTime("add operation");

        if (hash.containsKey(node))
        {
            removeNode(hash.get(node));
        }
        size++;
        hash.put(node, node);
        List<PuzzleMap> sameComplexity = complexityMap.get(node.getCoast());

        if (sameComplexity == null)
        {
            sameComplexity = new LinkedList<>();
            sameComplexity.add(node);
            complexityMap.put(node.getCoast(), sameComplexity);
        }
        else
        {
            sameComplexity.add(node);
        }
        order.add(node.getCoast());
        statHolder.endTime("add operation");
    }


    public boolean isEmpty()
    {
        return size == 0;
    }


    public PuzzleMap pollFirst()
    {
        statHolder.startTime("poll operation");
        int first = order.first();
        List<PuzzleMap> sameComplexity = complexityMap.get(first);

        PuzzleMap res = sameComplexity.get(0);
        removeNode(res);
        statHolder.endTime("poll operation");
        return res;
    }

    public boolean contains(PuzzleMap mapToSearch)
    {
        return hash.containsKey(mapToSearch);
    }

    public PuzzleMap get(PuzzleMap map)
    {
        return hash.get(map);
    }

    private void removeNode(PuzzleMap map)
    {
        statHolder.startTime("remove operation");
        if (hash.containsKey(map))
        {
            hash.remove(map);
            List<PuzzleMap> sameComplexity = complexityMap.get(map.getCoast());
            sameComplexity.remove(map);

            if (sameComplexity.isEmpty())
            {
                order.remove(map.getCoast());
            }
        }
        size--;
        statHolder.endTime("remove operation time");
    }

    public long size()
    {
        return size;
    }


}
