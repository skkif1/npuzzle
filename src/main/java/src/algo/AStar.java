package src.algo;

import src.map.PuzzleMap;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;


public class AStar
{

	private PuzzleMap finalState;

	private PuzzleMap res;

	private TreeSet<PuzzleMap> open = new TreeSet<>();

	private TreeSet<PuzzleMap> closed = new TreeSet<>();




	public PuzzleMap run(PuzzleMap start)
	{
		finalState = PuzzleMap.getFinalState();


		open.add(start);

		while (!open.isEmpty())
		{
			PuzzleMap currentNode = open.pollFirst();

			if (currentNode.equals(finalState))
			{
				return currentNode;
			}

			List<PuzzleMap> childNodes = currentNode.getPossibleMoves();

			for (PuzzleMap childNode : childNodes)
			{
				PuzzleMap temp;
				if (open.contains(childNode))
				{
					temp = open.subSet(childNode, childNode).first();

					if (temp.compareTo(childNode) <= 0)
					{
						// skip this node as it is persist in open list with better coast
						continue;
					}
				}


				if (closed.contains(childNode))
				{
					temp = open.subSet(childNode, childNode).first();

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
		return null;
	}

}
