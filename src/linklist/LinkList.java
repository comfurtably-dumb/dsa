package linklist;

import java.util.ArrayList;
import node.LinearNode;

public class LinkList <X extends Comparable<X>> {

    /*Data Members*/
    private LinearNode<X> head;
    private LinearNode<X> end;
    private int listSize;
    private boolean isSinglyLinked;
    private boolean isCircular;
    private static final int capacity = 1000000;

    /*Methods*/
    //Create
    public LinkList (boolean isSinglyLinked, boolean isCircular) {
        this.head = null;
        this.end = null;
        this.listSize = 0;
        this.isSinglyLinked = isSinglyLinked;
        this.isCircular = isCircular;
    }

    //Retrieve head
    public LinearNode<X> getHead () {
        return this.head;
    }

    //Retrieve end
    public LinearNode<X> getEnd () {
        return this.end;
    }

    //Retrieve node by index
    public LinearNode<X> getNodeAt (int index) throws Exception {
        if (!this.checkIndex(index)) {
            throw new Exception("ERROR: Index out of bounds");
        }
        int count = 0;
        LinearNode<X> currentNode = this.head;
        while (currentNode != this.end) {
            if (count == index) {
                return currentNode;
            }
            currentNode = currentNode.getNext();
            count++;
        }
        return currentNode;
    }

    //Update
    public void updateNodeAt (int index, LinearNode<X> node) throws Exception {
        if (!this.checkIndex(index)) {
            throw new Exception("ERROR: Index out of bounds");
        }
        int count = 0;
        LinearNode<X> currentNode = this.head;
        while (currentNode != this.end) {
            if (count == index) {
                currentNode.setData(node.getData());
                return;
            }
            currentNode = currentNode.getNext();
            count++;
        }
        currentNode.setData(node.getData());
    }

    //Search
    public boolean searchNode (LinearNode<X> nodeToSearch) {
        LinearNode<X> currentNode = this.head;
        while (currentNode != this.end) {
            if (currentNode.getData().equals(nodeToSearch.getData())) {
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return currentNode.getData().equals(nodeToSearch.getData());
    }

    //Search node first index
    public int searchFirstIndex (LinearNode<X> nodeToSearch) {
        int count = 0;
        LinearNode<X> currentNode = this.head;
        while (currentNode != this.end) {
            if (currentNode.getData().equals(nodeToSearch.getData())) {
                return count;
            }
            currentNode = currentNode.getNext();
            count++;
        }
        if (currentNode.getData().equals(nodeToSearch.getData())) {
            return count;
        }
        return -1;
    }

    //Search all indices
    public ArrayList<Integer> searchAllIndices (LinearNode<X> nodeToSearch) {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        int count = 0;
        LinearNode<X> currentNode = this.head;
        while (currentNode != this.end) {
            if (currentNode.getData().equals(nodeToSearch.getData())) {
                indices.add(count);
            }
            currentNode = currentNode.getNext();
            count++;
        }
        if (currentNode.getData().equals(nodeToSearch.getData())) {
            indices.add(count);
        }
        return indices;
    }

    //Add
    public void addNodeAt (int index, LinearNode<X> nodeToAdd) throws Exception {
        if (!this.checkIndex(index)) {
            throw new Exception("ERROR: Index out of bounds");
        }
        this.listSize++;
        if (this.listSize > capacity) {
            this.listSize--;
            throw new Exception("ERROR: List capacity exceeded");
        }
        int count = 0;
        LinearNode<X> currentNode = this.head;
        LinearNode<X> previousNode = null;
        while (count < index) {
            count++;
            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }
        if (count == 0) {
            this.head = nodeToAdd;
        } else if (count == this.listSize - 1) {
            this.end = nodeToAdd;
        }
        nodeToAdd.setNext(currentNode);
        if (previousNode != null) {
            previousNode.setNext(nodeToAdd);
        }
        if (!this.isSinglyLinked) {
            nodeToAdd.makeDoublyLinked();
            if (currentNode != null) {
                currentNode.setPrevious(nodeToAdd);
            }
            nodeToAdd.setPrevious(previousNode);
        }
    }

    //Delete
    public void deleteNodeAt (int index) throws Exception {
        if (!this.checkIndex(index)) {
            throw new Exception("ERROR: Index out of bounds");
        }
        this.listSize--;
        if (this.listSize < 0) {
            this.listSize++;
            throw new Exception("ERROR: Cannot delete from empty list");
        }
        int count = 0;
        LinearNode<X> nextNode;
        LinearNode<X> currentNode = this.head;
        LinearNode<X> previousNode = null;
        while (count < index) {
            count++;
            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }
        nextNode = currentNode.getNext();
        if (count == 0) {
            this.head = nextNode;
        } else if (count == this.listSize - 1) {
            this.end = nextNode;
        }
        if (previousNode != null) {
            previousNode.setNext(nextNode);
        }
        if (!this.isSinglyLinked && nextNode != null) {
            nextNode.setPrevious(previousNode);
        }
    }

    /*Public Utility*/
    //Size
    public int getSize () {
        return this.listSize;
    }

    //Is Singly Linked
    public boolean isListSinglyLinked () {
        return this.isSinglyLinked;
    }

    //Is Circular
    public boolean isListCircular () {
        return this.isCircular;
    }

    /*Error Handling*/
    //Index out of bounds
    private boolean checkIndex (int index) {
        if (index < 0 || index >= this.listSize) {
            return false;
        }
        return true;
    }

}
