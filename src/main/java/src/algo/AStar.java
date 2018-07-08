package src.algo;

import src.map.PuzzleMap;

import java.util.List;
import java.util.TreeMap;


public class AStar
{

	private PuzzleMap finalState;

	private PuzzleMap res;

	private TreeMap<Integer, PuzzleMap> open = new TreeMap<>();

	private TreeMap<Integer, PuzzleMap> closed = new TreeMap<>();




	public void run(PuzzleMap start)
	{
		finalState = PuzzleMap.getFinalState();


		open.put(start.getCoast(), start);

		while (!open.isEmpty())
		{
			PuzzleMap currentNode = open.pollFirstEntry().getValue();

			if (currentNode.equals(finalState))
			{
				res = currentNode;
				return;
			}

			List<PuzzleMap> childNodes = currentNode.getPossibleMoves();

			for (PuzzleMap childNode : childNodes)
			{
				open.put(childNode.getCoast(), childNode);
			}

			closed.put(currentNode.getCoast(), currentNode);
		}

	}

}
