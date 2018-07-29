package src;

import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import src.algo.*;
import src.input.InputManager;
import src.input.InputManagerException;
import src.map.PuzzleMap;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    private static Set<Pair<String, IHeuristicFunction>> mapFunction = new HashSet<>();

    private static ResultLogger logger = new ResultLogger();

    private static String FILE_LIST = "-fileList";

    public static void main(String[] args) {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));

        try {
            if (isFileList(argsList))
            {
                mapFunction.addAll(new FileLoader().getFiles(getFileListPath(argsList)));
            }
            else
            {
                getFunctions(argsList);
            }
        } catch (Exception ex) {
            help();
            System.exit(0);
        }

        try {
            run();
        } catch (Exception e) {
            if (e instanceof  InputManagerException)
            {
                System.out.println(e.getMessage());
            } else
            {
                System.out.println("something goes wrong (seems we cant solve this task). Ask why!");
            }
            System.exit(0);
        }
    }

    private static void getFunctions(List<String> args) {
        if (mapFunction.size() > 10)
        {
            throw new InputManagerException("max number of maps is 10");
        }
        for (int i = 0; i < args.size(); i++) {
            Pair<String, IHeuristicFunction> pair = new Pair<>(args.get(0), getFunction(args.get(1)));
            mapFunction.add(pair);
            args.remove(0);
            args.remove(0);
        }

    }

    private static void help() {
        System.out.println("./npuzzle <Path to valid file> <function> ....\n<<Functions>>\n-m : ManhattanDistanceHeuristicFunction\n-e : EuclideanDistanceHeuristicFunction\n-c : ChebyshevDistanceHeuristicFunction");
    }

    private static void run() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(mapFunction.size());
        List<Future<Pair<PuzzleMap, String>>> results = new ArrayList<>();

        for (Pair<String, IHeuristicFunction> pathFunction : mapFunction)
        {
            InputManager manager = new InputManager();
            AStar algo = new AStar();
            PuzzleMap initialState = checkMap(pathFunction.getKey(), pathFunction.getValue(), manager);
            algo.setInitialMap(initialState, pathFunction.getKey() + " " + initialState.getHeuristicFunction().getName());
            Future<Pair<PuzzleMap, String>> res = pool.submit(algo);
            results.add(res);
        }

        Set<Future<Pair<PuzzleMap, String>>> temp  = new HashSet<>();

        int done = 0;
        while (done != mapFunction.size()) {
            done = 0;
            for (Future<Pair<PuzzleMap, String>> result : results) {
                if (result.isDone())
                {
                    done++;
                    if (!temp.contains(result))
                    {
                        printRes(result);
                        temp.add(result);
                    }
                }


            }
        }
        pool.shutdown();
    }

    static IHeuristicFunction getFunction(String arg) {
        IHeuristicFunction heuristicFunction = null;

        if (arg.equalsIgnoreCase("-m")) {
            heuristicFunction = new ManhattanDistanceHeuristicFunction();
        } else if (arg.equalsIgnoreCase("-e")) {
            heuristicFunction = new EuclideanDistanceHeuristicFunction();
        } else if (arg.equalsIgnoreCase("-c")) {
            heuristicFunction = new ChebyshevDistanceHeuristicFunction();
        } else {
            throw new InputManagerException("bad function argument");
        }
        return heuristicFunction;
    }

    private static PuzzleMap checkMap(String path, IHeuristicFunction heuristicFunction, InputManager manager) {
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

    private static void printRes(Future<Pair<PuzzleMap, String>> result) throws ExecutionException, InterruptedException {

            result.get().getKey().printParentLine();
            printMapRes(result);
    }

    private static void printMapRes(Future<Pair<PuzzleMap, String>> res) throws ExecutionException, InterruptedException {

        String map = "stat for map -> " + res.get().getValue();
        String depth = "depth of solution : " + res.get().getKey().getH();
        System.out.println(map);
        System.out.println(depth);
        logger.logResult(map + "\n");
        logger.logResult(depth + "\n");

        for (StatHolder statHolder : StatHolder.getHolders()) {
            if (statHolder.getFilePath().equalsIgnoreCase(res.get().getValue())) {
                statHolder.printStat(logger);
            }
        }

        logger.logResult("\n");
        System.out.println();
    }

    private static boolean isFileList(List<String> args)
    {
        boolean is = false;

        for (String arg : args)
        {
            if (arg.equalsIgnoreCase(FILE_LIST))
                is = true;
        }
        if (args.size() != 2)
            throw new InputManagerException("in fileList mode <" + FILE_LIST + "> <path to file>");
        return is;
    }

    private static String getFileListPath(List<String> args)
    {
        for (String arg : args)
        {
            if (arg.equals(FILE_LIST))
                continue;
            return arg;
        }
        throw new InputManagerException("in fileList mode <" + FILE_LIST + "> <path to file>");
    }


}
