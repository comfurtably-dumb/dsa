package set;

import map.UnorderedMap;
import navigator.Navigator;

public class UnorderedSet <X> {
    
    /*Inner Class*/
    //Boolean Class
    class SetBoolean {

        /*Data Members*/
        boolean exists;

        /*Methods*/
        //Get Existence
        public boolean getExistence () {
            return this.exists;
        }

        //Set Existence
        public void setExistence () {
            this.exists = true;
        }

        //Equals
        public boolean equals (SetBoolean obj) {
            return this.exists == obj.getExistence();
        }
    }

    //Navigator
    class UnorderedSetNavigator extends Navigator<X> {

        /*Data Members*/
        private UnorderedMap<X, SetBoolean>.UnorderedMapNavigator mapNavigator;

        /*Methods*/
        //Constructor
        public UnorderedSetNavigator () {
            super.size = UnorderedSet.this.setSize;
            this.mapNavigator = UnorderedSet.this.mainMap.getNavigator(); 
        }

        //Get next element
        @Override
        public X getNextElement () throws Exception {
            return this.mapNavigator.getNextElement().getKeyObj();
        }

        //Get previous element
        @Override
        public X getPreviousElement () throws Exception {
            return this.mapNavigator.getPreviousElement().getKeyObj();
        }
    }
    
    /*Data Members*/
    private int setSize;
    private UnorderedMap<X, SetBoolean> mainMap = new UnorderedMap<X, SetBoolean>();
    private static final int capacity = 1000000;

    /*Methods*/
    //Create
    UnorderedSet () {
        this.setSize = 0;
    }

    //Search
    public boolean searchElement (X elementToSearch) {
        return this.mainMap.searchKey(elementToSearch);
    }

    //Add
    public void addElement (X elementToAdd) throws Exception {
        this.setSize++;
        if (this.setSize > capacity) {
            this.setSize--;
            throw new Exception("ERROR: Set capacity exceeded");
        }
        SetBoolean boolObj = new SetBoolean();
        boolObj.setExistence();
        this.mainMap.addKeyValuePair(elementToAdd, boolObj, false);
    }

    //Delete
    public void deleteElement (X elementToDelete) throws Exception {
        this.setSize--;
        if (this.setSize < 0) {
            this.setSize++;
            throw new Exception("ERROR: Cannot delete from an empty Set");
        }
        this.mainMap.deleteKey(elementToDelete);
    }

    //Union
    public UnorderedSet<X> union (UnorderedSet<X> set2) throws Exception{
        UnorderedSet<X> unionSet = new UnorderedSet<X>();
        return unionSet;
    }

    /*Public Utility*/
    //Size
    public int getsize () {
        return this.setSize;
    }

    //Navigator
    public UnorderedSetNavigator getNavigator () {
        return new UnorderedSetNavigator();
    }

    //Union
    public UnorderedSet<X> getUnion (UnorderedSet<X> set2) throws Exception {
        UnorderedSet<X> unionSet = new UnorderedSet<X>();
        UnorderedSetNavigator set1Navigator = this.getNavigator();
        UnorderedSetNavigator set2Navigator = set2.getNavigator();
        while (set1Navigator.hasNextElement()) {
            X elementToAdd = set1Navigator.getNextElement();
            unionSet.addElement(elementToAdd);
        }
        while (set2Navigator.hasNextElement()) {
            X elementToAdd = set2Navigator.getNextElement();
            unionSet.addElement(elementToAdd);
        }
        return unionSet;
    }

    //Intersection
    public UnorderedSet<X> getIntersection (UnorderedSet<X> set2) throws Exception {
        UnorderedSet<X> intersectionSet = new UnorderedSet<X>();
        UnorderedSetNavigator set1Navigator = this.getNavigator();
        while (set1Navigator.hasNextElement()) {
            X elementToAdd = set1Navigator.getNextElement();
            if (set2.searchElement(elementToAdd)) {
                intersectionSet.addElement(elementToAdd);
            }
        }
        return intersectionSet;
    }

    //Difference
    public UnorderedSet<X> getDifference (UnorderedSet<X> set2) throws Exception {
        UnorderedSet<X> differenceSet = new UnorderedSet<X>();
        UnorderedSetNavigator set1Navigator = this.getNavigator();
        while (set1Navigator.hasNextElement()) {
            X elementToAdd = set1Navigator.getNextElement();
            if (!set2.searchElement(elementToAdd)) {
                differenceSet.addElement(elementToAdd);
            }
        }
        return differenceSet;
    }
}
