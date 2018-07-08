package src;

import java.util.HashMap;
import java.util.TreeSet;



public class SortedPuzzleSet<E> extends TreeSet<E>
{
    HashMap<E, E> hash  = new HashMap<>();


    @Override
    public boolean add(E o)
    {

        if (this.contains(o))
        {
            Object toDell = hash.get(o);
            super.remove(toDell);
        }

        if (super.add(o))
        {
            hash.put(o, o);
            return true;
        }

        return false;
    }


    @Override
    public boolean contains(Object o)
    {
        return hash.keySet().contains(o);
    }

    public E get(Object o)
    {
        return hash.get(o);
    }

    @Override
    public E pollFirst()
    {
        E temp = super.pollFirst();
        hash.remove(temp);
        return temp;
    }

    @Override
    public int size()
    {
        if (super.size() != hash.size())
            throw new RuntimeException("Mismatch between wraper and HashTree");
        return hash.size();
    }
}
