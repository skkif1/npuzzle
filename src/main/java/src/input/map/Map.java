package src.input.map;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import src.input.algo.IHeruisticFunction;
import static java.lang.System.*;

public class Map {

    private static final int EMPTY_CELL = 0;

    private static int direction = 1;

    private static int size;

    private static Map finalState = null;

    private final String internal;

    private IHeruisticFunction heruisticFunction;

    private int coast = 0;

    private int h = 0;

    private int[][] map;

    private Map parent = null;

    private int iteratorI = 0;

    private int iteratorJ = 0;

    public Map(int[][] map, int mapSize) {
        this.map = map;
        size = mapSize;
        this.internal = createInternalString();
        findEmptyCell();
    }

    public Map(int[][] map, int mapSize, Map parent) {
        this(map, mapSize);
        this.h = parent.h++;
        this.parent = parent;
        this.heruisticFunction = parent.heruisticFunction;
        calculateCoast();
    }

    private void calculateCoast()
    {
        coast = h + heruisticFunction.calculateGCoast(this);
    }

    private String createInternalString() {
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

    private void findEmptyCell() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == EMPTY_CELL) {
                    iteratorI = i;
                    iteratorJ = j;
                }
            }
        }
    }

    public static Map getFinalState() {
        return finalState;
    }

    public static void generateFinalState() {
        finalState = new Map(findFinalState(), size);
    }

    private static Pair<Integer, Integer> getDirection(int i, int j, int[][] stateToSearch) {
        int ii = i;
        int jj = j;

        switch (direction) {
            case 1:
                jj++;
                break;
            case 2:
                ii++;
                break;
            case 3:
                jj--;
                break;
            case 4:
                ii--;
                break;
            default:
                break;
        }

        if (ii < size && ii >= 0 && jj < size && jj >= 0 && stateToSearch[ii][jj] == 0) {
            return new Pair<>(ii, jj);
        } else {
            direction = (direction == 4) ? 1 : direction + 1;
            return getDirection(i, j, stateToSearch);
        }
    }

    private static int[][] findFinalState() {
        int lastNum = size * size - 1;
        int[][] stateToSearch = new int[size][size];

        Pair<Integer, Integer> currentDirection = new Pair<>(0, 0);

        for (int i = 1; i <= lastNum; i++) {
            stateToSearch[currentDirection.getKey()][currentDirection.getValue()] = i;
            currentDirection = getDirection(currentDirection.getKey(), currentDirection.getValue(), stateToSearch);
        }
        return stateToSearch;
    }

    public String getInternal() {
        return internal;
    }

    public int[][] getMap() {
        return map;
    }

    public void setHeruisticFunction(IHeruisticFunction heruisticFunction) {
        this.heruisticFunction = heruisticFunction;
        calculateCoast();
    }

    public List<Map> getPossibleMoves() {
        List<Map> possibleMoves = new ArrayList<>();

        if (iteratorI + 1 < size) {
            possibleMoves.add(createChild(iteratorI + 1, iteratorJ));
        }
        if (iteratorI - 1 >= 0) {
            possibleMoves.add(createChild(iteratorI - 1, iteratorJ));
        }
        if (iteratorJ + 1 < size) {
            possibleMoves.add(createChild(iteratorI, iteratorJ + 1));
        }
        if (iteratorJ - 1 >= 0) {
            possibleMoves.add(createChild(iteratorI, iteratorJ - 1));
        }
        return possibleMoves;
    }

    private Map createChild(int iterI, int iterJ) {
        int[][] newMap = new int[size][size];

        for (int i = 0; i < size; i++) {
            arraycopy(map[i], 0, newMap[i], 0, size);
        }

        newMap[iterI][iterJ] = 0;
        newMap[iteratorI][iteratorJ] = map[iterI][iterJ];
        return new Map(newMap, size, this);
    }

    public int getCoast() {
        return coast;
    }

    public void printMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                out.print(map[i][j] + " ");
            }

            out.println();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Map && internal.equals(((Map) obj).internal);
    }

    @Override
    public int hashCode() {
        return internal.hashCode();
    }
}
