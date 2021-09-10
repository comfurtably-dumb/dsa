package deque;

import node.DequeNode;

public interface StackInterface <X> { 

    /*Methods*/
    public void pushBack (DequeNode<X> nodeToAdd) throws Exception;
    public DequeNode<X> popBack () throws Exception;
    
}
