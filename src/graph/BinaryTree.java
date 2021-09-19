package graph;

import node.BinaryNode;

public abstract class BinaryTree <X extends Comparable<X>> {

    /*Data Members*/
    private int treeSize;
    private static final int capacity = 500000;
    private BinaryNode<X> root;
    private boolean hasParentConnection;

    /*Methods*/
    //Create
    public BinaryTree (boolean hasParentConnection) {
        this.hasParentConnection = hasParentConnection;
        this.treeSize = 0;
        this.root = null;
    }

    //Retrieve
    public BinaryNode<X> getRoot () {
        return this.root;
    }

    //Search
    public boolean searchNode (BinaryNode<X> nodeToSearch) {
        return this.searchRecursive(this.root, nodeToSearch);
    }

    //Add
    public void addNode (BinaryNode<X> nodeToAdd) throws Exception {
        this.treeSize++;
        if (this.treeSize > capacity) {
            this.treeSize--;
            throw new Exception("ERROR: Tree capacity exceeded");
        }
        this.addImplementation(nodeToAdd);
    }

    /*Public Utility*/
    //Size
    public int getSize () {
        return this.treeSize;
    }

    //Has Parent Connection
    public boolean hasParentConnection () {
        return this.hasParentConnection;
    }

    /*Private Utility*/
    //Search
    private boolean searchRecursive (BinaryNode<X> currentNode, BinaryNode<X> nodeToSearch) {
        if (currentNode == null) {
            return false;
        } else if (currentNode.equals(nodeToSearch)) {
            return true;
        }
        return searchRecursive(currentNode.getLeftChild(), nodeToSearch) || searchRecursive(currentNode.getRightChild(), nodeToSearch);
    }

    //Add Implementation
    protected abstract void addImplementation (BinaryNode<X> nodeToAdd);
    
}
