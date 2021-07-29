package sequence;

import java.util.ArrayList;
import compare.Compare;

public class Sequence <X extends Compare> {
    
    /*Data Members*/
    private int sequenceSize;
    private ArrayList<X> mainSequence;
    private boolean hasFixedSize;
    private int totalSize;
    private String errorString;

    /*Methods*/
    //Create with Initial Size and Resizeability
    Sequence (X initializationValue, int initialSize, boolean hasFixedSize) throws Exception {
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
        for (int i = 0; i < this.sequenceSize; i++) {
            this.mainSequence.set(i, initializationValue);
        }
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
    public void insertElementAt (int index, X obj) throws Exception {
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
    public void sort (String sortType, boolean order) throws Exception {
        if (this.sequenceSize < 2) {
            return;
        }
        if (sortType.equals("selection")) {
            this.selectionSort(order);
            return;
        } 
        if (sortType.equals("inserion")) {
            this.insertionSort(order);
            return;
        } 
        if (sortType.equals("bubble")) {
            this.bubbleSort(order);
            return;
        } 
        if (sortType.equals("binary_insertion")) {
            this.binaryInsertionSort(order);
            return;
        } 
        if (sortType.equals("heap")) {
            this.heapSort(order);
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

    //Reverse
    public void reverse () {
        for (int i = 0; i < (this.sequenceSize - 1)/2; i++) {
            X temp = this.mainSequence.get(i);
            int j = this.sequenceSize - i - 1;
            this.mainSequence.set(i, this.mainSequence.get(j));
            this.mainSequence.set(j, temp);
        }
    }

    /*Private Utility*/
    //Binary Search Index
    private int binarySearchIndex (X elementToSearch, int startIndex, int endIndex, boolean order) {
        int middleIndex = 0;
        while (startIndex <= endIndex) {
            middleIndex = startIndex + (endIndex - startIndex)/2;
            X middleElement = this.mainSequence.get(middleIndex);
            if (elementToSearch.compareWith(middleElement) == -1) {
                if (order) {
                    endIndex = middleIndex - 1;
                } else {
                    startIndex = middleIndex + 1;
                }
            } else if (elementToSearch.compareWith(middleElement) == 1) {
                if (order) {
                    startIndex = middleIndex + 1;
                } else {
                    endIndex = middleIndex - 1;
                }
            } else {
                return middleIndex;
            }
        }
        return middleIndex;
    }

    //Heapify
    private void heapify (boolean order) {
        for (int i = 1; i < this.sequenceSize; i++) {
            heapInsert(i, order);
        }
    }
    
    //Heap Insert
    private void heapInsert (int currentHeapSize, boolean order) {
        int orderCheckValue = order ? 1 : -1;
        int j = currentHeapSize;
        int k = (currentHeapSize - 1)/2;
        while (k > -1) {
            X elementToInsert = this.mainSequence.get(j);
            X currentElement = this.mainSequence.get(k);
            if (elementToInsert.compareWith(currentElement) == orderCheckValue) {
                break;
            }
            this.mainSequence.set(j, currentElement);
            this.mainSequence.set(k, elementToInsert);
            j = k;
            if (j == 0) {
                break;
            }
            k = (k - 1)/2;
        }
    }

    //Heap Pop
    private X heapPop (int currentHeapSize, boolean order) {
        int orderCheckValue = order ? -1 : 1;
        X returnElement = this.mainSequence.get(0);
        X lastElement = this.mainSequence.get(currentHeapSize - 1);
        this.mainSequence.set(0, lastElement);
        int j = 0;
        int k = 1;
        while (k < currentHeapSize - 1) {
            X leftChild = this.mainSequence.get(k);
            X rightChild = this.mainSequence.get(k + 1);
            if (leftChild.compareWith(rightChild) == orderCheckValue) {
                if (lastElement.compareWith(leftChild) == orderCheckValue) {
                    this.mainSequence.set(k, lastElement);
                    this.mainSequence.set(j, leftChild);
                    j = k;
                } else {
                    break;
                }
            } else {
                if (lastElement.compareWith(rightChild) == orderCheckValue) {
                    k++;
                    this.mainSequence.set(k, lastElement);
                    this.mainSequence.set(j, leftChild);
                    j = k;
                } else {
                    break;
                }
            }
            k = 2*k + 1;
        }
        return returnElement;
    } 

    //Selection Sort
    private void selectionSort (boolean order) {
        int orderCheckValue = order ? 1 : -1;
        for (int i = 0; i < this.sequenceSize; i++) {
            X currentOrderElement = this.mainSequence.get(i);
            int currentOrderElementIndex = i;
            for (int j = i + 1; j < this.sequenceSize; j++) {
                X currentElement = this.mainSequence.get(j);
                if (currentOrderElement.compareWith(currentElement) == orderCheckValue) {
                        currentOrderElement = currentElement;
                        currentOrderElementIndex = j;
                }
            }
            X temp = this.mainSequence.get(i);
            this.mainSequence.set(i, currentOrderElement);
            this.mainSequence.set(currentOrderElementIndex, temp);
        }
    }

    //Insertion Sort
    private void insertionSort (boolean order) {
        int orderCheckValue = order ? 1 : -1;
        for (int i = 1; i < this.sequenceSize; i++) {
            X elementToInsert = this.mainSequence.get(i);
            int j;
            for (j = i - 1; j > -1; j--) {
                X currentElement = this.mainSequence.get(j);
                if (elementToInsert.compareWith(currentElement) == orderCheckValue) {
                    break;
                }
            }
            int insertIndex = j + 1;
            for (int k = i - 1; k >= insertIndex; k--) {
                this.mainSequence.set(k + 1, this.mainSequence.get(k));
            }
            this.mainSequence.set(insertIndex, elementToInsert);
        }
    }

    //Bubble Sort
    private void bubbleSort (boolean order) {
        int orderCheckValue = order ? -1 : 1;
        for (int i = 1; i < this.sequenceSize; i++) {
            for (int j = 0; j < this.sequenceSize - i; j++) {
                X currentElement = this.mainSequence.get(j);
                X nextElement = this.mainSequence.get(j + 1);
                if (nextElement.compareWith(currentElement) == orderCheckValue) {
                    this.mainSequence.set(j, nextElement);
                    this.mainSequence.set(j + 1, currentElement);
                }
            }
        }
    }

    //Binary Insertion Sort
    private void binaryInsertionSort (boolean order) {
        for (int i = 1; i < this.sequenceSize; i++) {
            X elementToInsert = this.mainSequence.get(i);
            int insertIndex = this.binarySearchIndex(elementToInsert, 0, i - 1, order);
            for (int k = i - 1; k >= insertIndex; k--) {
                this.mainSequence.set(k + 1, this.mainSequence.get(k));
            }
            this.mainSequence.set(insertIndex, elementToInsert);
        }
    }

    //Heap Sort
    private void heapSort (boolean order) {
        this.heapify(order);
        for (int i = 0; i < this.sequenceSize - 1; i++) {
            X poppedElement = this.heapPop(this.sequenceSize - i, order);
            this.mainSequence.set(this.sequenceSize - i - 1, poppedElement);
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
