package map;


public class PuzzleMap implements Comparable
{
    private final int [][] src;

    private final int size;

    private final PuzzleMap parent;

    private long f;

    private String hash;


    public PuzzleMap(int[][] src, int size)
    {
        this.src = src;
        this.size = size;
        this.parent = null;
        this.f = 0;
        generateHash();
    }


    public long getF()
    {
        return f;
    }



    private void generateHash()
    {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < size; i++)
        {

            for (int j = 0; j < size; j++)
            {
                temp = temp.append(String.valueOf(src[i][j]));
            }
        }
        hash = temp.toString();
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof PuzzleMap)) return false;

        PuzzleMap map = (PuzzleMap) o;

        return hash.equals(map.hash);
    }

    @Override
    public int hashCode()
    {
        return hash.hashCode();
    }

    @Override
    public int compareTo(Object o)
    {
       return (this.f - ((PuzzleMap)o).f) < 1 ? -1 : 1;
    }

}
