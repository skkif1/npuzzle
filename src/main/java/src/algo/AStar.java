package src.algo;

import src.map.PuzzleMap;

public class AStar
{

    /*
    *   one of the implementation of herustic function
    * */
    IHeruisticFunction heruisticFunction;

    public AStar(IHeruisticFunction heruisticFunction)
    {
        this.heruisticFunction = heruisticFunction;
    }

    public void run(PuzzleMap start)
    {
        System.out.println("A* algo execution with " + heruisticFunction.getClass() + " heruistic function");
    }

}
