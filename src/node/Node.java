package node;

import java.lang.Comparable;
import java.util.HashMap;

public class Node <V extends Comparable<V>, E extends Comparable<E>> {

    /*Data Members*/
    private int id;
    private V vertex;
    private HashMap<Integer, E> connectionMap;

    /*Methods*/
    //Constructor
    public Node (V vertex) {
        this.vertex = vertex;
    }

    //Get ID
    public int getId () {
        return this.id;
    }

    //Get Vertex
    public V getVertex () {
        return this.vertex;
    }

    //Set ID
    public void setId (int id) {
        this.id = id;
    }

    //Set Vertex
    public void setVertex (V vertex) {
        this.vertex = vertex;
    }

    //Get Connections
    public HashMap<Integer, E> getConnections () {
        return this.connectionMap;
    }

    //Get Edge connecting to ID
    public E getEdge (int id) {
        if (this.connectionMap.containsKey(id)) {
            return this.connectionMap.get(id);
        }
        return null;
    }

    //Add Connection
    public void addConnection (int id, E edge) throws Exception {
        if (this.id == id) {
            throw new Exception("ERROR: Self Connection is not allowed");
        }
        this.connectionMap.put(id, edge);
    }

    //Delete Connection
    public void deleteConnection (int id) {
        this.connectionMap.remove(id);
    }

}
