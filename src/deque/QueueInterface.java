package deque;

import node.DequeNode;

public interface QueueInterface <X> {
    
    /*Methods*/
    public void pushBack (DequeNode<X> nodeToAdd) throws Exception;
    public DequeNode<X> popFront () throws Exception;

}
