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

        //Set Value
        public void setValueObj (V valueObj) {
            this.valueObj = valueObj;
        }
    }
    
    /*Data Members*/
    private int mapSize;
    private static final int capacity = 1000000;
    private ArrayList<LinkedList<KeyValuePair>> mainTable = new ArrayList<LinkedList<KeyValuePair>>();

    /*Methods*/
    //Create
    UnorderedMap () {
        this.mapSize = 0;
    }

    //Retrieve
    public V getValueFromKey (K key) throws Exception {
        int index = key.hashCode()%capacity;
        LinkedList<KeyValuePair> keyValueList = this.mainTable.get(index);
        for (KeyValuePair kvPair: keyValueList) {
            K keyObj = kvPair.getKeyObj();
            if (keyObj.equals(key)){
                return kvPair.getValueObj();
            }
        }
        throw new Exception("ERROR: Key does not exist");
    }

    //Update
    public void updateValueForKey (K key, V value) throws Exception {
        int index = key.hashCode()%capacity;
        LinkedList<KeyValuePair> keyValueList = this.mainTable.get(index);
        for (KeyValuePair kvPair: keyValueList) {
            K keyObj = kvPair.getKeyObj();
            if (keyObj.equals(key)) {
                kvPair.setValueObj(value);
            }
        }
        throw new Exception("ERROR: Key does not exist");
    }

    //Search Key
    public boolean searchKey (K key) {
        int index = key.hashCode()%capacity;
        LinkedList<KeyValuePair> keyValueList = this.mainTable.get(index);
        for (KeyValuePair kvPair: keyValueList) {
            K keyObj = kvPair.getKeyObj();
            if (keyObj.equals(key)) {
                return true;
            }
        }
        return false;
    }

    //Search Value
    public boolean searchValue (V value) {
        for (int i = 0; i < capacity; i++) {
            LinkedList<KeyValuePair> keyValueList = this.mainTable.get(i);
            for (KeyValuePair kvPair: keyValueList) {
                V valueObj = kvPair.getValueObj();
                if (valueObj.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Search Keys for Value
    public ArrayList<K> searchKeysForValue (V value) {
        ArrayList<K> keyList = new ArrayList<K>();
        for (int i = 0; i < capacity; i++) {
            LinkedList<KeyValuePair> keyValueList = this.mainTable.get(i);
            for (KeyValuePair kvPair: keyValueList) {
                V valueObj = kvPair.getValueObj();
                K keyObj = kvPair.getKeyObj();
                if (valueObj.equals(value)) {
                    keyList.add(keyObj);
                }
            }
        }
        return keyList;
    }

    //Add
    public void addKeyValuePair (K key, V value, boolean newPair) throws Exception {
        this.mapSize++;
        if (this.mapSize > capacity) {
            this.mapSize--;
            throw new Exception("ERROR: Map capacity exceeded");
        }
        int index = key.hashCode()%capacity;
        LinkedList<KeyValuePair> keyValueList = this.mainTable.get(index);
        for (KeyValuePair kvPair: keyValueList) {
            K keyObj = kvPair.getKeyObj();
            if (keyObj.equals(key)) {
                if (newPair){
                    throw new Exception("ERROR: Key already exists");
                }
                kvPair.setValueObj(value);
                return;
            }
        }
        KeyValuePair kvPair = new KeyValuePair();
        kvPair.setKeyObj(key);
        kvPair.setValueObj(value);
        keyValueList.add(kvPair);
    }

    //Delete
    public void deleteKey (K key) throws Exception {
        this.mapSize--;
        if (this.mapSize < 0) {
            this.mapSize++;
            throw new Exception("ERROR: Cannot delete from an empty Map");
        }
        int index = key.hashCode()%capacity;
        LinkedList<KeyValuePair> keyValueList = this.mainTable.get(index);
        int i = 0;
        boolean keyExists = false;
        for (KeyValuePair kvPair: keyValueList) {
            K keyObj = kvPair.getKeyObj();
            if (keyObj.equals(key)) {
                keyExists = true;
                break;
            }
            i++;
        }
        if (keyExists) {
            keyValueList.remove(i);
            return;
        }
        throw new Exception("ERROR: Key does not exist");
    }

    /*Utils*/
    //Size
    public int getSize () {
        return this.mapSize;
    }
    
}
