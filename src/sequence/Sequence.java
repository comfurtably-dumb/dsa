//import java.lang.*;
import java.util.ArrayList;

public class Sequence<X> {
    
    /*Data Members*/
    private int sequenceSize;
    private ArrayList<X> mainSequence;
    private boolean hasFixedSize;
    private int totalSize;
    private String errorString;

    /*Methods*/
    //Create
    Sequence (int initialSize, boolean hasFixedSize) throws Exception {
        if (initialSize < 0) {
            this.errorString = "ERROR: Cannot initialize a Sequence of negative size";
            throw new Exception(this.errorString);
        }
        if (initialSize == 0 && hasFixedSize) {
            this.errorString = "ERROR: Cannot initialize a fixed size Sequence of size 0";
            throw new Exception(this.errorString);
        }
        this.sequenceSize = initialSize;
        this.totalSize = initialSize;
        this.hasFixedSize = hasFixedSize;
        if (!this.hasFixedSize) {
            this.totalSize = initialSize*3/2;
        }
        this.mainSequence = new ArrayList<X>(this.totalSize);
    }

    //Retrieve
    public X getElementAt (int index) throws Exception {
        if (this.checkIndex(index)) {
            return this.mainSequence.get(index);
        } else {
            this.errorString = "ERROR: Index out of bounds for the Sequence";
            throw new Exception(this.errorString);
        }
    }

    //Update
    public void updateElementAt (X obj, int index) throws Exception {
        if (this.checkIndex(index)) {
            this.mainSequence.set(index, obj);
        } else {
            this.errorString = "ERROR: Index out of bounds for the Sequence";
            throw new Exception(this.errorString);
        } 
    }

    //Add
    public void insertElementAt (X obj, int index) throws Exception {
        if (!this.isFixedSize()) {
            this.sequenceSize++;
            if (this.sequenceSize > this.totalSize) {
                this.totalSize = this.sequenceSize*3/2;
                ArrayList<X> newSequence = new ArrayList<X>(this.totalSize);
                for (int i = 0; i < this.sequenceSize; i++) {
                    newSequence.set(i, this.mainSequence.get(i));
                }
                this.mainSequence = newSequence; 
            }
            for (int i = this.sequenceSize - 1; i > index; i--) {
                this.mainSequence.set(i, this.mainSequence.get(i - 1));
            }
            this.mainSequence.set(index, obj);
        } else {
            this.errorString = "ERROR: Cannot change size of a fixed size Sequence";
            throw new Exception(this.errorString);
        }
    }

    //Delete
    public void deleteElementAt (int index) throws Exception {
        if (!this.isFixedSize()) {
            this.sequenceSize++;
            for (int i = index + 1; i < this.sequenceSize; i++) {
                this.mainSequence.set(i - 1, this.mainSequence.get(i));
            }
        } else {
            this.errorString = "ERROR: Cannot change size of a fixed size Sequence";
            throw new Exception(this.errorString);
        }
    }

    //Sort
    public void sort (String sortType, boolean order) throws Excpetion {
        if (this.sequenceSize < 2) {
            return;
        }
        if (sortType.equals("selection")) {
            this.selectionSort(order);
            return;
        } 
        if (sortType.equals("inserion")) {
            return;
        } 
        if (sortType.equals("bubble")) {
            return;
        } 
        if (sortType.equals("binary_inserion")) {
            return;
        } 
        if (sortType.equals("heap")) {
            return;
        } 
        if (sortType.equals("merge")) {
            return;
        } 
        if (sortType.equals("quick")) {
            return;
        }
        this.errorString = "ERROR: " + sortType + " is an invalid sort type";
        throw new Exception(this.errorString);
    }

    /*Public Utility*/
    //Size
    public int getSize () {
        return this.sequenceSize;
    }

    /*Private Utility*/
    //Selection Sort
    private void selectionSort (boolean order) {
        for (int i = 0; i < this.sequenceSize; i++) {
            X currentOrderElement = this.mainSequence.get(i);
            int currentOrderElementIndex = i;
            for (int j = i + 1; j < this.sequenceSize; j++) {
                X currentElement = this.mainSequence.get(j);
                if (order) {
                    if (currentElement.compareWith(currentOrderElement) == -1) {
                        currentOrderElement = currentElement;
                        currentOrderElementIndex = j;
                    }
                }
                else {
                    if (currentElement.compareWith(currentOrderElement) == 1) {
                        currentOrderElement = currentElement;
                        currentOrderElementIndex = j;
                    }
                }
            }
            X temp = this.mainSequence.get(i);
            this.mainSequence.add(i, currentOrderElement);
            this.mainSequence.add(currentOrderElementIndex, temp);
        }
    }

    /*Error Handling*/
    //Index out of bounds
    private boolean checkIndex (int index) {
        if (index < 0 || index > this.sequenceSize - 1) {
            return false;
        }
        return true;
    }

    //Fixed Size
    private boolean isFixedSize () {
        if (this.hasFixedSize) {
            return true;
        }
        return false;
    }
}