package src;

import javafx.util.Pair;
import src.algo.*;
import src.input.InputManager;
import src.map.PuzzleMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static final String macOS = "/Users/omotyliu/testMap1";

    private static final String windowsOS = "C:\\Users\\Oleksandr\\Desktop\\UNIT\\testMap.txt";

    private static Set<String> files = new HashSet<>();

    private static IHeuristicFunction heuristicFunction;

    public static void main(String[] args)
	{
        heuristicFunction = getFunction(args);

        if (heuristicFunction == null) {
            help();
            System.exit(0);
        }
        run();
    }

    private static IHeuristicFunction getFunction(String[] args) {
        IHeuristicFunction heuristicFunction = null;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-m")) {
                heuristicFunction = new ManhattanDistanceHeuristicFunction();
            } else if (arg.equalsIgnoreCase("-e")) {
                heuristicFunction =  new EuclideanDistanceHeuristicFunction();
            } else if (arg.equalsIgnoreCase("-c")) {
                heuristicFunction = new ChebyshevDistanceHeuristicFunction();
            }
            else if (arg.equalsIgnoreCase("-s"))
            {
                heuristicFunction = new SimpleHeuristicFunction();
            } else {
				files.add(arg);
            }
        }
        return heuristicFunction;
    }

    private static void help() {
    }

    private static void run()
    {
        ExecutorService pool = Executors.newFixedThreadPool(files.size());
        ArrayList<Future<Pair<PuzzleMap,String>>> results = new ArrayList<>();

        for (String path : files)
        {
            InputManager manager = new InputManager();
            AStar algo = new AStar();
            PuzzleMap initialState = checkMap(path, heuristicFunction, manager);
            algo.setInitialMap(initialState, path);
            Future<Pair<PuzzleMap, String>> res = pool.submit(algo);
            results.add(res);
        }

        int done = 0;
        while (done != files.size())
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
