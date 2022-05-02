package CustomLists;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomHashSet <T> implements CustomSet<T>{

    private static class Entry{
        //key is data we pass in
        Object key;

        //entry is pointer to next value
        Entry next;
    }

    //declare our underlying structure
    private Entry[] buckets;

    private int size;

    //when we instantiate the custom hash set we declare how big it will be
    //think about how much data you will store and determine you capacity accordingly
    public CustomHashSet(int capacity){
        size = 0;
        buckets = new Entry[capacity];
    }

    private int hashFunction(int hashCode){
        int index = hashCode;
        if(index < 0) index = -index;

        //return the remainder of dividing by the length, ensures that our index is within the proper bounds
        return index % buckets.length;
    }

    @Override
    public boolean add(T element) {
        int index = hashFunction(element.hashCode());

        //grab the entry at the specified index
        Entry current = buckets[index];

        //keep looping while current is not null (meaning that there is already a value there)
        //in lamen terms keep iterating till we find an empty spot
        while(current != null){
            //if we find the value that we are trying to insert, we return false because we aren't going to insert it again
            if(current.key.equals(element)) return false;

            //iterate to next entry
            current = current.next;
        }
        //after this loop, assume we've reached an empty spot and the element is not in the set

        Entry entry = new Entry();
        entry.key = element;

        //setup our next pointer
        //this line seems weird... should prob be index++... i think
        entry.next = buckets[index];

        //assigning the entry to this spot in the array
        buckets[index] = entry;

        //increment our size because we've added an element
        size++;

        return true;
    }

    @Override
    public boolean remove(T element) {
        int index = hashFunction(element.hashCode());
        Entry current = buckets[index];
        //keep track of previous so we can fix the link when we remove
        Entry previous = null;

        //while we have values:
        while(current != null){
            //look for element and remove it
            if(current.key.equals(element)){
                //break the chain and remove the element from the chain of elements

                if(previous == null){
                    buckets[index] = current.next;
                }
                else{
                    previous.next = current.next;
                }
                size--;
                return true;
            }

            //couldn't fucking read the rest of the teachers code for this part
            previous = current;
            current = current.next;
            //goddamn
        }

        return false;
    }

    //true if set contains element
    @Override
    public boolean contains(T element) {
        int index = hashFunction(element.hashCode());

        Entry current = buckets[index];

        while(current != null){
            if(current.key.equals(element)) return true;

            current = current.next;
        }
        //if we got through the loop and didn't find the element
        return false;
    }

    @Override
    public Iterator iterator() {
        return new CustomHashSetIterator();
    }

    class CustomHashSetIterator implements Iterator{
        private int currentBucket;
        private int previousBucket;
        private Entry currentEntry;
        private Entry previousEntry;

        public CustomHashSetIterator(){
            currentEntry = null;
            previousEntry = null;
            currentBucket = -1;
            previousBucket = -1;
        }

        //do we have another value
        @Override
        public boolean hasNext() {
            if(currentEntry != null && currentEntry.next != null){
                return true;
            }

            for(int index = currentBucket+1; index < buckets.length; index++){
                if(buckets[index] != null){
                    return true;
                }
            }

            //nothing left
            return false;
        }

        //return the next value, increment the iterator
        @Override
        public Object next() {
            previousEntry = currentEntry;
            previousBucket = currentBucket;

            //if either current node or next node ar null
            if(currentEntry == null || currentEntry.next == null){
                currentBucket++;

                while (currentBucket < buckets.length && buckets[currentBucket] == null){
                    currentBucket++;
                }

                if(currentBucket < buckets.length){
                    currentEntry = buckets[currentBucket];
                }
                else{
                    //if we try to access an element that isn't there
                    throw new NoSuchElementException();
                }
            }
            else{ //there is still at least one more element in the bucket
                currentEntry = currentEntry.next;
            }

            //return the next object
            return currentEntry.key;
        }
    }

    @Override
    public int size() {
        return this.size;
    }
}
