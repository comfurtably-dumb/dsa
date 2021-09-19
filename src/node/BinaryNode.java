package node;

public class BinaryNode <X extends Comparable<X>> {
    
    /*Data Members*/
    private X data;
    private BinaryNode<X> leftChild;
    private BinaryNode<X> rightChild;
    private BinaryNode<X> parent;
    private boolean hasParentConnection;

    /*Methods*/
    //Constructor
    public BinaryNode (boolean hasParentConnection) {
        this.hasParentConnection = hasParentConnection;
    }

    //Get data
    public X getData () {
        return this.data;
    }

    //Get left child
    public BinaryNode<X> getLeftChild () {
        return this.leftChild;
    }

    //Get right child
    public BinaryNode<X> getRightChild () {
        return this.rightChild;
    }

    //Get parent
    public BinaryNode<X> getParent () throws Exception {
        if (!hasParentConnection) {
            throw new Exception("ERROR: Node does not have connection to parent");
        }
        return this.parent;
    }

    //Set data
    public void setData (X data) {
        this.data = data;
    }

    //Set left child
    public void setLeftChild (BinaryNode<X> leftChild) {
        this.leftChild = leftChild;
    }

    //Set right child
    public void setRightChild (BinaryNode<X> rightChild) {
        this.rightChild = rightChild;
    }

    //Set parent
    public void setParent (BinaryNode<X> parent) throws Exception {
        if (!hasParentConnection) {
            throw new Exception("ERROR: Node does not have connection to parent");
        }
        this.parent = parent;
    }

}
