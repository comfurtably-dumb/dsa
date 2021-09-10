package deque;

import node.DequeNode;

public interface DequeInterface <X> {
    
    /*Methods*/
    public void pushBack (DequeNode<X> nodeToAdd) throws Exception;
    public void pushFront (DequeNode<X> nodeToAdd) throws Exception;
    public DequeNode<X> popBack () throws Exception;
    public DequeNode<X> popFront () throws Exception;

}
