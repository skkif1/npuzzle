package src;

import src.algo.*;
import src.input.InputManager;
import src.map.PuzzleMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static final String macOS = "/Users/mivanov/testMap1";

    private static final String windowsOS = "C:\\Users\\Oleksandr\\Desktop\\UNIT\\testMap.txt";

    private static List<String> files = new ArrayList<>();

    private static IHeuristicFunction heuristicFunction;

    public static void main(String[] args) {
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
        ArrayList<Future<PuzzleMap>> results = new ArrayList<>();

        for (String path : files) {
            InputManager manager = new InputManager();
            AStar algo = new AStar();
            PuzzleMap initialState = checkMap(path, heuristicFunction, manager);
            algo.setInitialMap(initialState);
            Future<PuzzleMap> res = pool.submit(algo);
            results.add(res);
        }

        int done = 0;
        while (done != files.size())
        {
            done = 0;
            for (Future<PuzzleMap> result : results)
            {
                if (result.isDone())
                    done++;
            }
        }

        printRes(results);
    }


    private static void printRes(List<Future<PuzzleMap>> results)
    {
        for (Future<PuzzleMap> result : results)
        {
            try
            {
                result.get().printMap();
            }
            catch (InterruptedException | ExecutionException e) {
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

}
