package graph;

import node.Node;

public class Tree <V extends Comparable<V>, E extends Comparable<E>> extends Graph<V, E> {
    
    /*Data Members*/
    private Node<V, E> root;

    /*Methods*/
    //Create
    public Tree (boolean isDirected) {
        super(isDirected);
    }

    //Retrieve
    public Node<V, E> getRoot () {
        return this.root;
    }

    //Add
    public void addNode (int parentId, Node<V, E> nodeToAdd, E edgeToAdd) throws Exception {
        super.addNode(nodeToAdd);
        super.addConnection(parentId, super.getSize(), edgeToAdd);
    }

    /*Banned Methods*/
    //Add Connection
    @Override
    public void addConnection(int id1, int id2, E edgeToAdd) throws Exception {
        throw new Exception("ERROR: Method disallowed");
    }

    //Delete Connection
    @Override
    public void deleteConnection(int id1, int id2) throws Exception {
        throw new Exception("ERROR: Method disallowed");
    }

}
