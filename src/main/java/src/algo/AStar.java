package src.algo;

import src.SortedPuzzleSet;
import src.map.PuzzleMap;

import java.util.List;

public class AStar
{

	private PuzzleMap finalState;

	private PuzzleMap res;

	private SortedPuzzleSet open = new SortedPuzzleSet();

	private SortedPuzzleSet closed = new SortedPuzzleSet();

	private long execTime;

	static long pollCount = 0;

	static long count = 0;

	static long pollTime = 0;

	static long variantCreation = 0;

	static long addTime  = 0;

	static long closedAddTime  = 0;

	static long getTime = 0;

	public long getExecTime()
	{
		return execTime;
	}

	public PuzzleMap run(PuzzleMap start)
	{
		execTime = System.currentTimeMillis();
		finalState = PuzzleMap.getFinalState();


		open.add(start);

		while (!open.isEmpty())
		{
			count++;
			long stat = System.currentTimeMillis();
			long tempSize = open.size();
			PuzzleMap currentNode = open.pollFirst();
			if (open.size() != tempSize - 1)
				System.out.println("");
			pollTime += System.currentTimeMillis() - stat;

			if (currentNode.equals(finalState))
			{
				execTime = System.currentTimeMillis() - execTime;
				System.out.println(execTime);
				System.out.println(pollTime);
				System.out.println(variantCreation);
				System.out.println(addTime);
				System.out.println(closedAddTime);
				System.out.println(getTime);
				return currentNode;
			}

			stat = System.currentTimeMillis();
			List<PuzzleMap> childNodes = currentNode.getPossibleMoves();
			variantCreation += System.currentTimeMillis() - stat;

			for (PuzzleMap childNode : childNodes)
			{
				PuzzleMap temp;

				if (open.contains(childNode))
				{
					stat = System.currentTimeMillis();
					temp = open.get(childNode);
					getTime = System.currentTimeMillis() - stat;

					if (temp.compareTo(childNode) <= 0)
					{
						// skip this node as it is persist in open list with better coast
						continue;
					}
				}


				if (closed.contains(childNode))
				{
					temp = closed.get(childNode);

					if (temp.compareTo(childNode) <= 0)
					{
						// skip this node as such consist closed list with better coast
						continue;
					}
				}

				stat = System.currentTimeMillis();
				open.add(childNode);
				addTime = System.currentTimeMillis() - stat;
			}
			stat = System.currentTimeMillis();

			closed.add(currentNode);
			closedAddTime = System.currentTimeMillis() - stat;

		}
		return null;
	}

}
