package src;

import java.util.ArrayList;

import java.util.HashMap;
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



    public void add(PuzzleMap node)
    {

        hash.put(node, node);
        List<PuzzleMap> sameComplexity = complexityMap.get(node.getCoast());
        if (sameComplexity == null)
        {
            sameComplexity = new ArrayList<>();
            sameComplexity.add(node);
            if (!complexityMap.containsKey(node))
                size++;
            complexityMap.put(node.getCoast(), sameComplexity);
            order.add(node.getCoast());

        }else
        {
            sameComplexity.add(node);
            size++;
        }

    }


    public boolean isEmpty()
    {
        return size == 0;
    }


    public PuzzleMap pollFirst()
    {
        if (size > 0)
        {

            PuzzleMap res = complexityMap.get(order.first()).get(0);
            removeNode(res);
            return res;
        }
        return null;
    }

    public boolean contains(PuzzleMap mapToSearch)
    {
        return hash.keySet().contains(mapToSearch);
    }

    public PuzzleMap get(PuzzleMap map)
    {
        return hash.get(map);
    }

    private void removeNode(PuzzleMap map)
    {
        if (hash.containsKey(map))
        {
            hash.remove(map);
            List<PuzzleMap> sameComplexity = complexityMap.get(map.getCoast());
            sameComplexity.remove(map);
            if (sameComplexity.size() == 0)
                order.remove(map.getCoast());
            size--;
        }
    }

    public long size()
    {
        return size;
    }






}
