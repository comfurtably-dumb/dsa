package graph;

import java.util.HashMap;
import java.util.Map;
import node.Node;

public class Tree <V extends Comparable<V>, E extends Comparable<E>> extends Graph<V, E> {
    
    /*Data Members*/
    private Node<V, E> root;

    /*Methods*/
    //Create
    public Tree (boolean isDirected) {
        super(isDirected);
        this.root = null;
    }

    //Retrieve
    public Node<V, E> getRoot () {
        return this.root;
    }

    //Add
    public void addNode (int parentId, Node<V, E> nodeToAdd, E edgeToAdd) throws Exception {
        if (parentId < 0 || parentId >= super.graphSize) {
            throw new Exception("ERROR: ID out of bounds");
        }
        super.addNode(nodeToAdd);
        super.addConnection(parentId, super.getSize(), edgeToAdd);
    }

    //Delete
    public void deleteNode (int id) throws Exception {
        super.graphSize--;
        if (super.graphSize < 0) {
            super.graphSize++;
            throw new Exception("ERROR: Cannot delete from an empty Tree");
        }
        if (id < 0 || id >= super.graphSize) {
            throw new Exception("ERROR: ID out of bounds");
        }
        boolean[] visitedArray = new boolean[super.graphSize + 1];
        this.dfsDelete(0, visitedArray, id);
        super.nodeList.remove(id);
        if (super.graphSize == 0) {
            this.root = null;
        }
    }

    /*Private Utility*/
    //DFS
    private void dfsDelete (int startingId, boolean[] visitedArray, int id) throws Exception {
        HashMap<Integer, E> connectionMap = super.getNodeByID(startingId).getConnections();
        if (connectionMap.containsKey(id)) {
            HashMap<Integer, E> childConnectionMap = super.getNodeByID(id).getConnections();
            connectionMap.putAll(childConnectionMap);
            connectionMap.remove(id);
            return;
        }
        for (Map.Entry<Integer, E> entry : connectionMap.entrySet()) {
            if (!visitedArray[entry.getKey()]) {
                visitedArray[entry.getKey()] = true;
                dfsDelete(entry.getKey(), visitedArray, id);
            }
        }
    }

    /*Disallowed Methods*/
    //Add Node
    @Override
    public void addNode (Node <V, E> nodeToAdd) throws Exception {
        throw new Exception("ERROR: Method disallowed");
    }

    //Delete node by ID
    @Override
    public void deleteNodeById (int id) throws Exception {
        throw new Exception("ERROR: Method disallowed");
    }

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
