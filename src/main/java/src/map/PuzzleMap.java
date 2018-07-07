package src.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;
import src.algo.IHeuristicFunction;

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

	public static int size;

	private static PuzzleMap finalState = null;

	private final String internal;

	private IHeuristicFunction heuristicFunction;

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
		this.heuristicFunction = parent.heuristicFunction;
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
		coast = h + heuristicFunction.calculateGCoast(this, finalState);
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

	public void setHeuristicFunction(IHeuristicFunction heuristicFunction) {
		this.heuristicFunction = heuristicFunction;
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

//    def inversion(size, puzzle):
//    inv = 0
//            for i in range(size * size - 1):
//            for j in range(i + 1, size * size):
//            if puzzle[i] > puzzle[j] and puzzle[i] != 0 and puzzle[j] != 0:
//    inv += 1
//            return inv
//
//    def is_solvable(size, goalmatrix, puzzlematrix):
//    goal, puzzle = [], []
//            for rawgoal, rawmatrix in zip(goalmatrix, puzzlematrix):
//            for g,p in zip(rawgoal, rawmatrix):
//            goal.append(g)
//            puzzle.append(p)
//    inv_goal = inversion(size, goal)
//    inv_puzzl = inversion(size, puzzle)
//
//    if size % 2 == 0:
//    inv_goal += goal.index(0)
//    inv_puzzl += puzzle.index(0)
//
//            return (inv_puzzl % 2 == inv_goal % 2)

	public boolean isSolvable() {
		int[] finalStateArray = new int[size * size];
		int[] currentStateArray = new int[size * size];
		int k = 0;
		for (int[] ints : map) {
			for (int i : ints) {
				currentStateArray[k++] = i;
			}
		}
		k = 0;
		for (int[] ints : finalState.map) {
			for (int i : ints) {
				finalStateArray[k++] = i;
			}
		}
		int currentInv = getInversionsCount(currentStateArray);
		int finalInv = getInversionsCount(finalStateArray);
		if (size % 2 == 0) {
			for (int i = 0; i < finalStateArray.length; i++) {
				if (finalStateArray[i] == 0) {
					finalInv += i;
					break;
				}
			}
			for (int i = 0; i < currentStateArray.length; i++) {
				if (currentStateArray[i] == 0) {
					currentInv += i;
					break;
				}
			}
		}
		return finalInv % 2 == currentInv % 2;
	}

	private int getInversionsCount(int[] map) {
		int inversions = 0;
		for (int i = 0; i < map.length - 1; i++) {
			for (int j = i + 1; j < map.length; j++) {
				if (map[i] == 0 || map[j] == 0) continue;
				if (map[i] > map[j]) inversions++;
			}
		}
		return inversions;
	}

	public boolean isSolved() {
		return finalState.equals(this);
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
