package map;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.LinkedList;

public class UnorderedMap <K extends Comparable <K>, V extends Comparable <V>> {

    /*Inner Class*/
    //Key Class
    class KeyValuePair {

        /*Data Members*/
        K keyObj;
        V valueObj;

        /*Constructor*/
        KeyValuePair (K keyObj, V valueObj) {
            this.keyObj = keyObj;
            this.valueObj = valueObj;
        }

        /*Methods*/
        //Get Key
        public K getKeyObj () {
            return this.keyObj;
        }

        //Set Key
        public void setKeyObj (K keyObj) {
            this.keyObj = keyObj;
        }

        //Get Value
        public V getValueObj () {
            return this.valueObj;
        }

        //Set Key
        public void setValueObj (V valueObj) {
            this.valueObj = valueObj;
        }
    }
    
    /*Data Members*/
    private int mapSize;
    private final int capacity = 1000000;
    private ArrayList<LinkedList<V>> mainTable = new ArrayList<LinkedList<V>>();

    /*Methods*/
    //Create
    UnorderedMap () {
        this.mapSize = 0;
    }

    //Retrieve
    public V getValueForKey (K key) throws Exception {
        int index = key.hashCode()%capacity;
        V value = null;
        return value;
    }
    
}
