package src.input;

import java.util.ArrayList;
import java.util.List;

public class Map
{
    private static int size;

    private int [][] map;

    private static final int EMPTY_CELL = 0;

    private int iteratorI = 0;

    private int iteratorJ = 0;

    private final String  internal;


    public Map(int[][] map, int size)
    {
        this.map = map;
        Map.size = size;
        this.internal = createInternalString();
        createInternalString();
        findEmtyCell();
    }


    private void findEmtyCell()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (map[i][j] == EMPTY_CELL)
                {
                    iteratorI = i;
                    iteratorJ = j;
                }
            }
        }
    }

    public List<Map> getPossibleMoves()
    {
        List<Map> possibleMoves = new ArrayList<>();

        if (iteratorI + 1 < size)
        {
            possibleMoves.add(createChild(iteratorI + 1, iteratorJ));
        }
        if (iteratorI - 1 >= 0)
        {
            possibleMoves.add(createChild(iteratorI - 1, iteratorJ));
        }
        if (iteratorJ + 1 < size)
        {
            possibleMoves.add(createChild(iteratorI, iteratorJ + 1));
        }
        if (iteratorJ + 1 >= 0)
        {
            possibleMoves.add(createChild(iteratorI, iteratorJ - 1));
        }
        return possibleMoves;
    }


    private Map createChild(int iterI, int iterJ)
    {
        int [][] newMap  = new int[size][size];

        for (int i = 0; i < size; i++)
        {
            System.arraycopy(map[i], 0, newMap[i], 0, size);
        }

        newMap[iterI][iterJ] = 0;
        newMap[iteratorI][iteratorJ] = map[iterI][iterJ];
        return new Map(newMap, size);
    }

    public void printMap()
    {

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size ; j++)
            {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    private String createInternalString()
    {
        String temp = "";
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                 temp += (String.valueOf(map[i][j]));
            }
        }
        return temp;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Map && internal.equals(((Map) obj).internal);
    }

    @Override
    public int hashCode()
    {
        return internal.hashCode();
    }
}
