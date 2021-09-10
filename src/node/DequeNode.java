package node;

public class DequeNode <X> {

    /*Data Members*/
    private X data;
    private DequeNode<X> next;
    private DequeNode<X> previous;

    /*Metods*/
    //Constructor
    public DequeNode () {
        this.previous = null;
        this.next = null;
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
    public DequeNode<X> getNext () {
        return this.next;
    }
    
    //Set next
    public void setNext (DequeNode<X> next) {
        this.next = next;
    }

    //Get previous
    public DequeNode<X> getPrevious () {
        return this.previous;
    }
    
    //Set next
    public void setPrevious (DequeNode<X> previous) {
        this.previous = previous;
    }

}