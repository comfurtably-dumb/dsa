package sequence;

import java.util.ArrayList;
import navigator.Navigator;
import java.lang.Comparable;
import java.lang.Math;

public class Sequence <X extends Comparable <X>> {
    
    /*Inner Class*/
    //Sequence Navigator
    class SequenceNavigator extends Navigator<X> {
        
        /*Methods (Implementations)*/
        //Constructor
        public SequenceNavigator () {
            super.size = Sequence.this.sequenceSize;
        }

        //Get next element
        @Override
        public X getNextElement () throws Exception {
            if (this.hasNextElement()) {
                this.currentIndex++;
                return Sequence.this.mainSequence.get(currentIndex);
            }
            throw new Exception("ERROR: Navigator out of bounds");
        }

        //Get previous element
        @Override
        public X getPreviousElement () throws Exception {
            if (this.hasPreviousElement()) {
                this.currentIndex--;
                return Sequence.this.mainSequence.get(currentIndex);
            }
            throw new Exception("ERROR: Navigator out of bounds");
        }
    }
    
    /*Data Members*/
    private int sequenceSize;
    private ArrayList<X> mainSequence;
    private boolean hasFixedSize;
    private int totalSize;
    private final int capacity = 1000000;

    /*Methods*/
    //Create
    Sequence (X initializationValue, int initialSize, boolean hasFixedSize) throws Exception {
        if (initialSize < 0) {
            throw new Exception("ERROR: Cannot initialize a Sequence of negative size");
        }
        if (initialSize == 0 && hasFixedSize) {
            throw new Exception("ERROR: Cannot initialize a fixed size Sequence of size 0");
        }
        if (initialSize > this.capacity) {
            throw new Exception("ERROR: Sequence Capacity exceeded");
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
            throw new Exception("ERROR: Index out of bounds for the Sequence");
        }
    }

    //Update
    public void updateElementAt (X obj, int index) throws Exception {
        if (this.checkIndex(index)) {
            this.mainSequence.set(index, obj);
        } else {
            throw new Exception("ERROR: Index out of bounds for the Sequence");
        } 
    }

    //Search
    public boolean searchElement (X elementToSearch) { 
        for (int i = 0; i < this.sequenceSize; i++){
            if (this.mainSequence.get(i).equals(elementToSearch)) {
                return true;
            }
        }
        return false;
    }

    //Search first occurence
    public int searchElementFirstIndex (X elementToSearch) {
        for (int i = 0; i < this.sequenceSize; i++) {
            if (this.mainSequence.get(i).equals(elementToSearch)) {
                return i;
            }
        }
        return -1;
    }

    //Search index
    public ArrayList<Integer> searchElementIndices (X elementToSearch) {
        ArrayList<Integer> indicesArray = new ArrayList<Integer>();
        for (int i = 0; i < this.sequenceSize; i++) {
            if (this.mainSequence.get(i).equals(elementToSearch)) {
                indicesArray.add(i);
            }
        }
        return indicesArray;
    }

    //Add
    public void insertElementAt (int index, X obj) throws Exception {
        if (!this.isFixedSize()) {
            this.sequenceSize++;
            if (this.sequenceSize > this.capacity) {
                this.sequenceSize--;
                throw new Exception("ERROR: Sequence Capacity exceeded");
            }
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
            throw new Exception("ERROR: Cannot change size of a fixed size Sequence");
        }
    }

    //Delete
    public void deleteElementAt (int index) throws Exception {
        if (!this.isFixedSize()) {
            this.sequenceSize--;
            if (this.sequenceSize < 0) {
                this.sequenceSize++;
                throw new Exception("ERROR: Cannot delete from an empty Sequence");
            }
            for (int i = index + 1; i < this.sequenceSize; i++) {
                this.mainSequence.set(i - 1, this.mainSequence.get(i));
            }
        } else {
            throw new Exception("ERROR: Cannot change size of a fixed size Sequence");
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
            this.mergeSort(order);
            return;
        } 
        if (sortType.equals("quick")) {
            this.quickSort(order);
            return;
        };
        throw new Exception("ERROR: " + sortType + " is an invalid sort type");
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

    //Navigator
    public SequenceNavigator createNavigator () {
        return new SequenceNavigator();
    }

    /*Private Utility*/
    //Binary Search Index
    private int binarySearchIndex (X elementToSearch, int startIndex, int endIndex, boolean order) {
        int middleIndex = 0;
        while (startIndex <= endIndex) {
            middleIndex = startIndex + (endIndex - startIndex)/2;
            X middleElement = this.mainSequence.get(middleIndex);
            if (elementToSearch.compareTo(middleElement) == -1) {
                if (order) {
                    endIndex = middleIndex - 1;
                } else {
                    startIndex = middleIndex + 1;
                }
            } else if (elementToSearch.compareTo(middleElement) == 1) {
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
            if (elementToInsert.compareTo(currentElement) == orderCheckValue) {
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
            if (leftChild.compareTo(rightChild) == orderCheckValue) {
                if (lastElement.compareTo(leftChild) == orderCheckValue) {
                    this.mainSequence.set(k, lastElement);
                    this.mainSequence.set(j, leftChild);
                    j = k;
                } else {
                    break;
                }
            } else {
                if (lastElement.compareTo(rightChild) == orderCheckValue) {
                    k++;
                    this.mainSequence.set(k, lastElement);
                    this.mainSequence.set(j, rightChild);
                    j = k;
                } else {
                    break;
                }
            }
            k = 2*k + 1;
        }
        return returnElement;
    }

    //Split and Merge
    private void splitAndMerge (int startIndex, int endIndex, boolean order) {
        int orderCheckValue = order ? -1 : 1;
        if (startIndex == endIndex) {
            return;
        }
        int middleIndex = startIndex + (endIndex - startIndex)/2;
        this.splitAndMerge(startIndex, middleIndex, order);
        this.splitAndMerge(middleIndex + 1, endIndex, order);
        int i = startIndex, j = middleIndex + 1, k = 0, currentSize = endIndex - startIndex + 1;
        ArrayList<X> auxArray = new ArrayList<X>(currentSize);
        while ((i <= middleIndex || j <= endIndex) && k < currentSize) {
            if (i <= middleIndex && j <= endIndex) {
                X iElement = this.mainSequence.get(i);
                X jElement = this.mainSequence.get(j);
                if (iElement.compareTo(jElement) == orderCheckValue) {
                    auxArray.set(k, iElement);
                    i++;
                } else {
                    auxArray.set(k, jElement);
                    j++;
                }
            } else if (i > middleIndex && j <= endIndex) {
                X jElement = this.mainSequence.get(j);
                auxArray.set(k, jElement);
                j++;
            } else if (i <= middleIndex && j > endIndex) {
                X iElement = this.mainSequence.get(i);
                auxArray.set(k, iElement);
            }
            k++;
            for (int p = 0; p < currentSize; p++) {
                this.mainSequence.set(p, auxArray.get(p));
            }
        }
    }

    //Pivot Position and Partition
    private void pivotPositionAndPartition (int startIndex, int endIndex, boolean order) {
        int orderCheckValue = order ? -1 : 1;
        if (startIndex >= endIndex) {
            return;
        }
        int pivotIndex = (int)Math.random()*(endIndex - startIndex) + startIndex;
        X pivotElement = this.mainSequence.get(pivotIndex);
        this.mainSequence.set(pivotIndex, this.mainSequence.get(startIndex));
        this.mainSequence.set(startIndex, pivotElement);
        int i = startIndex, j = endIndex;
        while (i < j) {
            do {
                i++;
            } while (pivotElement.compareTo(this.mainSequence.get(i)) == orderCheckValue);
            do {
                j--;
            } while (pivotElement.compareTo(this.mainSequence.get(j)) == -orderCheckValue);
            this.mainSequence.set(i, this.mainSequence.get(j));
            this.mainSequence.set(j, this.mainSequence.get(i));
        }
        this.mainSequence.set(startIndex, this.mainSequence.get(j));
        this.mainSequence.set(j, pivotElement);
        this.pivotPositionAndPartition(startIndex, j, order);
        this.pivotPositionAndPartition(j + 1, endIndex, order);
    } 

    //Selection Sort
    private void selectionSort (boolean order) {
        int orderCheckValue = order ? 1 : -1;
        for (int i = 0; i < this.sequenceSize; i++) {
            X currentOrderElement = this.mainSequence.get(i);
            int currentOrderElementIndex = i;
            for (int j = i + 1; j < this.sequenceSize; j++) {
                X currentElement = this.mainSequence.get(j);
                if (currentOrderElement.compareTo(currentElement) == orderCheckValue) {
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
                if (elementToInsert.compareTo(currentElement) == orderCheckValue) {
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
                if (nextElement.compareTo(currentElement) == orderCheckValue) {
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

    //Merge Sort
    private void mergeSort (boolean order) {
        this.splitAndMerge(0, this.sequenceSize - 1, order);
    }

    //Quick Sort
    private void quickSort (boolean order) {
        this.pivotPositionAndPartition(0, this.sequenceSize, order);
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
