package src.algo;

import src.map.PuzzleMap;

public interface IHeruisticFunction
{

    /*
    *  return the G value (herustic result)
    * */
    int calculateGCoast(PuzzleMap state, PuzzleMap finalState);
}
