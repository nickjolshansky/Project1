package CustomLists;

//linked lists are stored using nodes
//each node has a value as well as a pointer to the next node
//every linked list has a head or start of the list
public class CustomLinkedList<T> implements List<T> {
    public CustomLinkedList(){
        this.head = null;
    }

    Node head;
    class Node{ //every node has some data and a pointer to the next node=
        T data;
        Node next;
        Node(T data){
            this.data = data;
        }
    }

    @Override
    public void add(T element) {
        //case 1: we have empty list

        if(this.head == null){
            this.head = new Node(element);
            this.head.next = null;
        }
        else{
            Node placeHolderNode = this.head;
            while(true){
                if(placeHolderNode.next == null){
                    placeHolderNode.next = new Node(element);
                    break;
                }
                else{
                    placeHolderNode = placeHolderNode.next;
                }
            }
        }
    }

    @Override
    public T get(int i) {
        if(i < 0){
            throw new IndexOutOfBoundsException("No negative values");
        }

        Node placeHolderNode = this.head;
        for(int j = 0; j < i; j++){
            if(placeHolderNode == null){
                throw new IndexOutOfBoundsException("Index: " +i+ "Size: " + j);
            }

            placeHolderNode = placeHolderNode.next;
        }

        return placeHolderNode.data;
    }

    @Override
    public void print() {
        Node placeHolderNode = this.head;

        while(placeHolderNode != null){
            System.out.print(placeHolderNode.data + ", ");
            placeHolderNode = placeHolderNode.next;
        }
    }
}
