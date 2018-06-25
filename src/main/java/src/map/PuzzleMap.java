package src.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import src.algo.IHeruisticFunction;
import static java.lang.System.*;

/*
*  Represents the state of the Map
*  every instance has a link to its parent
*  Do not use PuzzleMap constructor more than one time
*  generate the G of A* algo for current state
*
* */

public class PuzzleMap implements Comparable {

    private static final int EMPTY_CELL = 0;

    private static int direction = 1;

    private static int size;

    private static PuzzleMap finalState = null;

    private final String internal;

    private IHeruisticFunction heruisticFunction;

    private int coast = 0;

    private int h = 0;

    private int[][] map;

    private PuzzleMap parent = null;

    private int iteratorI = 0;

    private int iteratorJ = 0;

    public PuzzleMap(int[][] map, int mapSize, PuzzleMap parent) {
        this(map, mapSize);
        this.h = parent.h++;
        this.parent = parent;
        this.heruisticFunction = parent.heruisticFunction;
        calculateCoast();
    }

    /*
    *   Call for this constructor only once than call
     *
     *   getPossibleMoves() to generate new Maps
    *
    * */
    public PuzzleMap(int[][] map, int mapSize) {
        this.map = map;
        size = mapSize;
        this.internal = createInternalString();
        findEmptyCell();
    }

    private void calculateCoast() {
        coast = h + heruisticFunction.calculateGCoast(this, finalState);
    }

    private String createInternalString() {
        String temp = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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

    public static PuzzleMap getFinalState() {
        return finalState;
    }

    public static void generateFinalState() {
        finalState = new PuzzleMap(findFinalState(), size);
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

    /*
    *   return state of the current map in string
     *   1  2  3
     *   8     4
     *   7  6  5
     *
     *   will return 12345678
    * */
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

    public List<PuzzleMap> getPossibleMoves() {
        List<PuzzleMap> possibleMoves = new ArrayList<>();

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

    private PuzzleMap createChild(int iterI, int iterJ) {
        int[][] newMap = new int[size][size];

        for (int i = 0; i < size; i++) {
            arraycopy(map[i], 0, newMap[i], 0, size);
        }

        newMap[iterI][iterJ] = 0;
        newMap[iteratorI][iteratorJ] = map[iterI][iterJ];
        return new PuzzleMap(newMap, size, this);
    }

    public int getCoast() {
        return coast;
    }

    public void printParentLine() {
        LinkedList<PuzzleMap> line = new LinkedList<>();

        PuzzleMap temp = this;
        while (temp != null) {
            line.add(temp);
            temp = temp.parent;
        }
        for (Iterator<PuzzleMap> it = line.descendingIterator(); it.hasNext(); ) {
            PuzzleMap state = it.next();
            state.printMap();
            System.out.println("///////////");
        }
    }

    /*
    *   should print to std path which show result
    * */

    public void printMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                out.print(map[i][j] + " ");
            }

            out.println();
        }
    }

    /*
    *   should say if current map is solvable (not working)
    * */
    public boolean isSolvable() {
        String temp = internal;
        temp = StringUtils.replace(temp, "0", "");
        char internalArr[] = temp.toCharArray();
        int inversions = 0;
        for (int i = 0; i < internalArr.length; i++) {

            for (int j = i + 1; j < internalArr.length; j++) {
                if (internalArr[j] > internalArr[i]) {
                    inversions++;
                }
            }
        }

        boolean res = false;

        if ((size % 2 != 0) && (inversions % 2 == 0)) {
            res = true;
        } else if ((size % 2 == 0)) {
            if (iteratorI % 2 == 0 && inversions % 2 == 1)
                res = true;
            if (iteratorI % 2 == 1 && inversions % 2 == 0)
                res = true;
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PuzzleMap)) return false;

        PuzzleMap map = (PuzzleMap) o;

        return internal.equals(map.internal);
    }

    @Override
    public int hashCode() {
        return internal.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        return (this.coast - ((PuzzleMap) o).coast) < 1 ? -1 : 1;
    }
}
