package src;

import javafx.util.Pair;
import src.algo.*;
import src.input.InputManager;
import src.map.PuzzleMap;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static List<Pair<String, IHeuristicFunction>> mapFunction = new ArrayList<>();

    public static void main(String[] args)
	{
        ArrayList<String > argsList = new ArrayList<>(Arrays.asList(args));

        try
        {
            getFunctions(argsList);
        }
        catch (Exception ex)
        {
            help();
            System.exit(0);
        }

        run();
    }


    private static void getFunctions(List<String> args)
    {
        for (int i = 0; i < args.size(); i++)
        {
            Pair<String, IHeuristicFunction> pair = new Pair<>(args.get(0), getFunction(args.get(1)));
            mapFunction.add(pair);
            args.remove(0);
            args.remove(0);
        }
    }

    private static IHeuristicFunction getFunction(String arg) {
        IHeuristicFunction heuristicFunction = null;

            if (arg.equalsIgnoreCase("-m")) {
                heuristicFunction = new ManhattanDistanceHeuristicFunction();
            } else if (arg.equalsIgnoreCase("-e")) {
                heuristicFunction =  new EuclideanDistanceHeuristicFunction();
            } else if (arg.equalsIgnoreCase("-c")) {
                heuristicFunction = new ChebyshevDistanceHeuristicFunction();
            }
            else if (arg.equalsIgnoreCase("-n"))
            {
                heuristicFunction = new SimpleHeuristicFunction();
            }else
            {
                throw new RuntimeException();
            }
            return heuristicFunction;
    }

    private static void help()
    {
        System.out.println("./npuzzle <Path to valid file> <function> ....\n<<Functions>>\n-m : ManhattanDistanceHeuristicFunction\n-e : EuclideanDistanceHeuristicFunction\n-c : ChebyshevDistanceHeuristicFunction\n-n : ManhattanDistanceConflictsHeuristicFunction");
    }

    private static void run()
    {
        ExecutorService pool = Executors.newFixedThreadPool(mapFunction.size());
        ArrayList<Future<Pair<PuzzleMap,String>>> results = new ArrayList<>();

        for (Pair<String, IHeuristicFunction> pathFunction : mapFunction)
        {
            InputManager manager = new InputManager();
            AStar algo = new AStar();
            PuzzleMap initialState = checkMap(pathFunction.getKey(), pathFunction.getValue(), manager);
            algo.setInitialMap(initialState, pathFunction.getKey());
            Future<Pair<PuzzleMap, String>> res = pool.submit(algo);
            results.add(res);
        }

        int done = 0;
        while (done != mapFunction.size())
        {
            done = 0;
            for (Future<Pair<PuzzleMap,String>> result : results)
            {
                if (result.isDone())
                    done++;
            }
        }
		pool.shutdown();
        printRes(results);
    }


    private static void printRes(List<Future<Pair<PuzzleMap,String>>> results)
    {
        for (Future<Pair<PuzzleMap,String>> result : results)
        {
            try
            {
                result.get().getKey().printParentLine();
                printMapRes(result);
            }
            catch (InterruptedException | ExecutionException e)
			{
                System.out.println("something went wrong");
            }
        }
    }

    private static PuzzleMap checkMap(String path, IHeuristicFunction heuristicFunction, InputManager manager)
    {
        int[][] map = manager.getMapNumbers(path);
        manager.setMapSize(map.length);
        PuzzleMap initialState = new PuzzleMap(map, manager.getMapSize());
        initialState.generateFinalState();
        boolean isSolvable = initialState.isSolvable();

        if (!isSolvable) {
            System.out.println("The map >>> " + path + " <<< is unsolvable");
            help();
            System.exit(0);
            return null;
        }

        initialState.setHeuristicFunction(heuristicFunction);
        return initialState;
    }

    private static void printMapRes(Future<Pair<PuzzleMap,String>> res) throws ExecutionException, InterruptedException
	{

		System.out.println("stat for map -> " + res.get().getValue());
		System.out.println("depth of solution : " + res.get().getKey().getH());
		for (StatHolder statHolder : StatHolder.getHolders())
		{
			if (statHolder.getFilePath().equalsIgnoreCase(res.get().getValue()))
			{
				statHolder.printStat();
			}
		}

		System.out.println();
	}

}
