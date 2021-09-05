package graph;

import node.Node;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Graph <V extends Comparable<V>, E extends Comparable<E>> {

    /*Data Members*/
    private int graphSize;
    private ArrayList<Node<V, E>> nodeList;
    private boolean isDirected;
    private static final int capacity = 1000;

    /*Methods*/
    //Create
    public Graph (boolean isDirected) {
        this.graphSize = 0;
        this.isDirected = isDirected;
    }

    //Retrieve Node by ID
    public Node<V, E> getNodeByID (int id) throws Exception {
        if (!this.checkId(id)) {
            throw new Exception("ERROR: ID out of bounds");
        }
        return this.nodeList.get(id);
    }

    //Get Connections from Node
    public HashMap<Node<V, E>, E> getConnectionsFromNode (int id) throws Exception {
        HashMap<Integer, E> connectionMap = this.getNodeByID(id).getConnections();
        HashMap<Node<V, E>, E> connections = new HashMap<Node<V, E>, E>();
        for (Map.Entry<Integer, E> entry : connectionMap.entrySet()) {
            Node<V, E> connectingNode = this.getNodeByID(entry.getKey());
            E connectingEdge = entry.getValue();
            connections.put(connectingNode, connectingEdge);
        }
        return connections;
    }

    //Get Edge ID1 to ID2
    public E getEdge (int id1, int id2) throws Exception {
        if (!this.checkId(id2)) {
            throw new Exception("ERROR: ID out of bounds");
        }
        Node<V, E> node1 = this.getNodeByID(id1);
        return node1.getEdge(id2);
    }

    //Update Node by ID
    public void updateNodeById (int id, Node<V, E> node) throws Exception {
        Node<V, E> nodeToUpdate = this.getNodeByID(id);
        nodeToUpdate.setVertex(node.getVertex());
    }

    //Add Node
    public void addNode (Node<V, E> nodeToAdd) throws Exception {
        this.graphSize++;
        if (this.graphSize > capacity) {
            this.graphSize--;
            throw new Exception("ERROR: Graph capacity exceeded");
        }
        nodeToAdd.setId(this.graphSize);
        this.nodeList.add(nodeToAdd);
    }

    //Delete Node by ID
    public void deleteNodeById (int id) throws Exception {
        this.graphSize--;
        if (this.graphSize < 0) {
            this.graphSize++;
            throw new Exception("ERROR: Cannot delete from an empty Graph");
        }
        Queue<Integer> bfsQueue = new LinkedList<Integer>();
        boolean[] visitedArray = new boolean[this.graphSize];
        for (int i = 0; i < this.graphSize; i++) {
            bfsQueue.add(i);
            visitedArray[i] = true;
            Node<V, E> currentNode = this.getNodeByID(id);
            while (!bfsQueue.isEmpty()) {
                bfsQueue.poll();
                for (Map.Entry<Integer, E> entry : currentNode.getConnections().entrySet()) {
                    int connectingId = entry.getKey();
                    if (connectingId != id && !visitedArray[connectingId]) {
                        bfsQueue.add(connectingId);
                        visitedArray[connectingId] = true;
                    } else if (connectingId == id) {
                        currentNode.getConnections().remove(id);
                    }
                }
            }
        }
    }

    //Add Connection from ID1 to ID2
    public void addConnection (int id1, int id2, E edgeToAdd) throws Exception {
        if (!this.checkId(id2)) {
            throw new Exception("ERROR: ID out of bounds");
        }
        Node<V, E> node1 = this.nodeList.get(id1);
        node1.addConnection(id2, edgeToAdd);
        if (!this.isDirected) {
            Node<V, E> node2 = this.getNodeByID(id2);
            node2.addConnection(id1, edgeToAdd);
        }
    }

    //Delete Connection from ID1 to ID2
    public void deleteConnection (int id1, int id2) throws Exception {
        if (!(this.checkId(id1) && this.checkId(id2))) {
            throw new Exception("ERROR: ID out of bounds");
        }
        Node<V, E> node1 = this.nodeList.get(id1);
        HashMap<Integer, E> node1ConnectionMap = node1.getConnections();
        if (node1ConnectionMap.containsKey(id2)) {
            node1ConnectionMap.remove(id2);
        }
        if (!this.isDirected) {
            Node<V, E> node2 = this.nodeList.get(id2);
            HashMap<Integer, E> node2ConnectionMap = node2.getConnections();
            if (node2ConnectionMap.containsKey(id1)) {
                node1ConnectionMap.remove(id2);
            }
        }
    }

    /*Error Handling*/
    //ID out of bounds
    private boolean checkId (int id) {
        if (id < 0 || id > this.graphSize) {
            return false;
        }
        return true;
    }

}