package graph;

import node.Node;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import navigator.Navigator;

public class Graph <V extends Comparable<V>, E extends Comparable<E>> {

    /*Inner Class*/
    //Graph Navigator
    class GraphNavigator extends Navigator<Node<V, E>> {
        
        /*Methods*/
        //Constructor
        public GraphNavigator () {
            super.size = Graph.this.graphSize;
        }

        //Get next element
        @Override
        public Node<V, E> getNextElement() throws Exception {
            if (this.hasNextElement()) {
                this.currentIndex++;
                return Graph.this.nodeList.get(this.currentIndex);
            }
            throw new Exception("ERROR: Navigator out of bounds");
        }

        //Get previous element
        @Override
        public Node<V, E> getPreviousElement() throws Exception {
            if (this.hasPreviousElement()) {
                this.currentIndex--;
                return Graph.this.nodeList.get(this.currentIndex);
            }
            throw new Exception("ERROR: Navigator out of bounds");
        }

    }
    
    /*Data Members*/
    protected int graphSize;
    protected ArrayList<Node<V, E>> nodeList;
    private boolean isDirected;
    private static int capacity = 1000;

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

    //Search
    public boolean searchNode (Node<V, E> nodeToSearch) throws Exception {
        for (int i = 0; i < this.graphSize; i++) {
            if (nodeToSearch.equals(this.nodeList.get(i))) {
                return true;
            }
        }
        return false;
    }

    //Search first ID
    public int searchFirstId (Node<V, E> nodeToSearch) {
        for (int i = 0; i < this.graphSize; i++) {
            if (nodeToSearch.equals(this.nodeList.get(i))) {
                return i;
            }
        }
        return -1;
    }

    //Search all IDs
    public ArrayList<Integer> searchAllIds (Node<V, E> nodeToSearch) {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        for (int i = 0; i < this.graphSize; i++) {
            if (nodeToSearch.equals(this.nodeList.get(i))) {
                idList.add(i);
            }
        }
        return idList;
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
        if (!this.checkId(id)) {
            throw new Exception("ERROR: ID out of bounds");
        }
        Queue<Integer> bfsQueue = new LinkedList<Integer>();
        boolean[] visitedArray = new boolean[this.graphSize + 1];
        for (int i = 0; i < this.graphSize + 1; i++) {
            bfsQueue.add(i);
            visitedArray[i] = true;
            Node<V, E> currentNode = this.getNodeByID(i);
            while (!bfsQueue.isEmpty()) {
                bfsQueue.poll();
                HashMap<Integer, E> connectionMap = currentNode.getConnections();
                Iterator<Map.Entry<Integer, E>> iterator = connectionMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, E> entry = iterator.next();
                    int connectingId = entry.getKey();
                    if (connectingId != id && !visitedArray[connectingId]) {
                        bfsQueue.add(connectingId);
                        visitedArray[connectingId] = true;
                    } else if (connectingId == id) {
                        connectionMap.remove(id);
                    }
                }
            }
        }
        this.nodeList.remove(id);
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

    /*Public Utility*/
    //Size
    public int getSize () {
        return this.graphSize;
    }

    //Is Directed
    public boolean isGraphDirected () {
        return this.isDirected;
    }

    //Navigator
    public GraphNavigator createGraphNavigator () {
        return new GraphNavigator();
    }

    /*Error Handling*/
    //ID out of bounds
    private boolean checkId (int id) {
        if (id < 0 || id >= this.graphSize) {
            return false;
        }
        return true;
    }

}