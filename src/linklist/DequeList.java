package linklist;

import deque.DequeInterface;
import node.DequeNode;
import navigator.Navigator;

public class DequeList <X> implements DequeInterface<X> {

    /*Inner Class*/
    //LinkList Navigator
    class DequeListNavigator extends Navigator<X> {

        /*Data Members*/
        private DequeNode<X> currentElement;
        
        /*Methods*/
        //Constructor
        public DequeListNavigator () {
            super.size = DequeList.this.listSize;
            this.currentElement = DequeList.this.head;
        }

        //Get next element
        @Override
        public X getNextElement() throws Exception {
            if (this.hasNextElement()) {
                this.currentIndex++;
                this.currentElement = this.currentElement.getNext();
                return this.currentElement.getData();
            }
            throw new Exception("ERROR: Navigator out of bounds");
        }

        //Get previous element 
        @Override
        public X getPreviousElement() throws Exception {
            if (this.hasPreviousElement()) {
                this.currentIndex--;
                this.currentElement = this.currentElement.getPrevious();
                return this.currentElement.getData();
            }
            throw new Exception("ERROR: Navigator out of bounds");
        }
    }
    
    /*Data Members*/
    private DequeNode<X> head;
    private DequeNode<X> end;
    private int listSize;
    private static final int capacity = 1000000;

    /*Methods*/
    //Create
    public DequeList () {
        this.head = null;
        this.end = null;
        this.listSize = 0;
    }

    //Get head
    public DequeNode<X> getHead () {
        return this.head;
    }

    //Get end
    public DequeNode<X> getEnd () {
        return this.end;
    }

    //Search
    public boolean searchNode (DequeNode<X> nodeToSearch) {
        DequeNode<X> currentNode = this.head;
        while (currentNode != this.end) {
            if (currentNode.getData().equals(nodeToSearch.getData())) {
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return currentNode.getData().equals(nodeToSearch.getData());
    }

    //Push back
    @Override
    public void pushBack (DequeNode<X> nodeToAdd) throws Exception {
        this.listSize++;
        if (this.listSize > capacity) {
            this.listSize--;
            throw new Exception("ERROR: Capacity exceeded");
        }
        this.end.setNext(nodeToAdd);
        nodeToAdd.setPrevious(this.end);
        this.end = nodeToAdd;
        if (this.listSize == 1) {
            this.head = nodeToAdd;
        }
    }

    //Push front
    @Override    
    public void pushFront (DequeNode<X> nodeToAdd) throws Exception {
        this.listSize++;
        if (this.listSize > capacity) {
            this.listSize--;
            throw new Exception("ERROR: List capacity exceeded");
        }
        nodeToAdd.setNext(this.head);
        this.head.setPrevious(nodeToAdd);
        this.head = nodeToAdd;
        if (this.listSize == 1) {
            this.end = nodeToAdd;
        }
    }

    //Pop back
    @Override
    public DequeNode<X> popBack () throws Exception {
        this.listSize--;
        if (this.listSize < 0) {
            this.listSize++;
            throw new Exception("ERROR: Cannot delete from empty list");
        }
        DequeNode<X> nodeToReturn = this.end;
        this.end = this.end.getPrevious();
        if (this.end != null) {
            this.end.setNext(null);
        } else {
            this.head = null;
        }
        return nodeToReturn;
    }

    //Pop front
    @Override
    public DequeNode<X> popFront () throws Exception {
        this.listSize--;
        if (this.listSize < 0) {
            this.listSize++;
            throw new Exception("ERROR: Cannot delete from empty list");
        }
        DequeNode<X> nodeToReturn = this.head;
        this.head = this.head.getNext();
        if (this.head != null) {
            this.head.setPrevious(null);
        } else {
            this.end = null;
        }
        return nodeToReturn;
    }

    /*Public Utility*/
    //Size
    public int getSize () {
        return this.listSize;
    }

}
