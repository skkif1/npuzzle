package src.algo;

import src.SortedPuzzleSet;
import src.map.PuzzleMap;

import java.util.List;
import java.util.TreeSet;


public class AStar
{

	private PuzzleMap finalState;

	private PuzzleMap res;

	private SortedPuzzleSet<PuzzleMap> open = new SortedPuzzleSet<>();

	private SortedPuzzleSet<PuzzleMap> closed = new SortedPuzzleSet<>();

	private long execTime;

	static int count = 1;

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
			PuzzleMap currentNode = open.pollFirst();

			if (currentNode.equals(finalState))
			{
				execTime = System.currentTimeMillis() - execTime;
				return currentNode;
			}

			List<PuzzleMap> childNodes = currentNode.getPossibleMoves();

			count += childNodes.size();

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
		}
		System.out.println(count);
		return null;
	}

}
