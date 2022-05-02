package CustomLists;

import java.util.Iterator;

public interface CustomSet<T> {

    //return true if we insert, false otherwise (can't have duplicates)
    boolean add(T element);

    //return true if we remove it, false otherwiese
    boolean remove(T element);

    //returns true if the set contains the element
    boolean contains(T element);

    //returns an iterator over all of the elements in the set
    Iterator iterator();

    //how any elements are in the set
    int size();
}
