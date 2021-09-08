package node; 

public class LinearNode <X extends Comparable<X>> {

    /*Data Members*/
    private X data;
    private LinearNode<X> next;
    private LinearNode<X> previous;
    private boolean isSinglyLinked;

    /*Methods*/
    //Constructor
    public LinearNode (boolean isSinglyLinked) {
        this.isSinglyLinked = isSinglyLinked;
    }

    //Get data
    public X getData () {
        return this.data;
    }

    //Set data
    public void setData (X data) {
        this.data = data;
    }

    //Get next
    public LinearNode<X> getNext () {
        return this.next;
    }
    
    //Set next
    public void setNext (LinearNode<X> next) {
        this.next = next;
    }

    //Get previous
    public LinearNode<X> getPrevious () throws Exception {
        if (this.isSinglyLinked) {
            throw new Exception("ERROR: Node is singly linked");
        }
        return this.previous;
    }
    
    //Set next
    public void setPrevious (LinearNode<X> previous) throws Exception {
        if (this.isSinglyLinked) {
            throw new Exception("ERROR: Node is singly linked");
        }
        this.previous = previous;
    }

    //Make doubly linked
    public void makeDoublyLinked () {
        this.isSinglyLinked = false;
    }
    
}
