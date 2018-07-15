package src.algo;

import org.apache.commons.lang3.StringUtils;
import src.map.PuzzleMap;

import java.util.Arrays;

public class OmotyliuHeuristicFunction implements IHeuristicFunction
{
    int currentMovable = '1';

    int finalIteratorI = 0;

    int finalIteratorJ = 0;

    int currentIteratorI = 0;

    int currentIteratorJ = 0;

    int[][] finalState;

    int[][] currentState;


    @Override
    public int calculateGCoast(PuzzleMap state, PuzzleMap finalState)
    {
        this.finalState = finalState.getMap();
        this.currentState = state.getMap();
        getCurrentMovable();
        System.out.println(getDistance());
        return 0;
    }

    public static void main(String[] args)
    {


    }

    private void getCurrentMovable()
    {
//        int finalState[][] = {{1,2,3}, {8,0,4}, {7,6,5}};
//        int currentState[][] = {{3,2,6}, {1,4,0}, {8,7,5}};

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (currentState[i][j] != finalState[i][j])
                {
                    currentMovable = finalState[i][j];
                    System.out.println(currentMovable);
                    finalIteratorI = i;
                    finalIteratorJ = j;
                    return;
                }
            }
        }
    }


    private int getDistance()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (currentState[i][j] == currentMovable)
                {
                    currentIteratorI = i;
                    currentIteratorJ = j;
                }
            }
        }

        return Math.abs(finalIteratorI - currentIteratorI) + Math.abs(finalIteratorJ - currentIteratorJ);
    }

}
