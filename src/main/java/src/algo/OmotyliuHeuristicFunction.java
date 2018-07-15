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

    private int  lastMovable;


    @Override
    public int calculateGCoast(PuzzleMap state, PuzzleMap finalState)
    {
        this.finalState = finalState.getMap();
        this.currentState = state.getMap();
        int res = 0;

        getCurrentMovable();

        if (currentMovable < lastMovable)
        res += 3;
        res += getDistance();

        lastMovable = currentMovable;

        return res;
    }


    private void getCurrentMovable()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (currentState[i][j] != finalState[i][j])
                {
                    currentMovable = finalState[i][j];
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
