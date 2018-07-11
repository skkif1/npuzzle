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

			PuzzleMap currentNode = open.pollFirst();

			pollCount += System.currentTimeMillis() - stat;
			if (currentNode.equals(finalState))
			{
				execTime = System.currentTimeMillis() - execTime;
				return currentNode;
			}

			List<PuzzleMap> childNodes = currentNode.getPossibleMoves();


			for (PuzzleMap childNode : childNodes)
			{
				PuzzleMap temp;
				if (open.contains(childNode))
				{
					temp = open.get(childNode);

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


				open.add(childNode);
			}

			closed.add(currentNode);
			if (count % 10000 == 0)
			{
				System.out.println(pollCount);
				pollCount = 0;
			}

		}
		return null;
	}

}
