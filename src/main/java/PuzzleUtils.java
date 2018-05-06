
class PuzzleUtils
{
    static void printMap(int [][] state, int size)
    {

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size ; j++)
            {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }

    }
}
