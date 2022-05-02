package CustomLists;
import java.util.Arrays;

//that <T> indicates that this class can be of any type
public class CustomArrayList <T> implements List<T>{
    //how many objects are in array list
    private int size = 0;

    private static final int DEFAULT_CAPACITY = 10;

    private Object elements[];

    public CustomArrayList(){
        elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(T element){
        if(size == elements.length){
            resize();
        }

        elements[size++] = element;
    }

    private void resize(){
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    //return element at index
    @Override
    public T get(int i){
        if(i >= size || i < 0){
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }

        return (T) elements[i];
    }

    @Override
    public void print(){
        for(Object e : elements){
            if(e != null){
                System.out.println(e);
            }
        }
    }
}
