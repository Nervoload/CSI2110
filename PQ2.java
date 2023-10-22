/* ---------------------------------------------------------------------------------
PQ2.java

Written by John Surette
StudentID: 300307306
CSI 2110
Programming Assignment 1
Date: 2023-10-21

This class implements a priority queue using an ArrayList organized as a Heap and a Comparator.
It compares the elements in the heap using the Comparator and organizes the heap accordingly.

Dependencies:
    
    PointSet.java       - A class containing methods to read in data from files
    LabelledPoint.java  - A class representing a point in a vector space with a label and key

------------------------------------------------------------------------------------*/

import java.util.ArrayList;
import java.util.Comparator;

public class PQ2<E> implements PriorityQueueIF<E> {

    private ArrayList<E> heapArray;
    private Comparator<E> comparator;

    public PQ2(int k, Comparator<E> comparator) {
        
        this.comparator = comparator;
        heapArray = new ArrayList<>(k);

    }

    public E peek() {

        return heapArray.isEmpty() ? null : heapArray.get(0);
    }

    public int size() {
        return heapArray.size();
    }

    public boolean isEmpty() {
        return heapArray.isEmpty();
    }

    // upHeap for adding elements to the heap

    private void upHeap() {

        int childIndex = heapArray.size() - 1;

        while (childIndex > 0) {

            int parentIndex = (childIndex - 1) / 2;

            E parent = heapArray.get(parentIndex);
            E child = heapArray.get(childIndex);
            
            if (comparator.compare(child, parent) <= 0) {
                break;
            }
            heapArray.set(childIndex, parent);
            heapArray.set(parentIndex, child);

            childIndex = parentIndex;
        }
    }

    // downHeap for removing elements from the heap

    private void downHeap() {
        int parentIndex = 0;
        int last = heapArray.size() - 1;
        E parent = heapArray.get(parentIndex);

        // Compare parent with its children; swap if necessary

        while (true) {
            int left = 2 * parentIndex + 1;        // organize the heap 
            int right = 2 * parentIndex + 2;

            // Finding the larger child

            if (left > last) {      // if left child is greater than the last element in the heap, break
                break;
            }
            
            // if right child is greater than the last element in the heap, set max to left child

            int max= left;
            if (right<= last && comparator.compare(heapArray.get(right), heapArray.get(left)) > 0) {
                max= right;
            }

            // if max is greater than parent, swap

            if (comparator.compare(heapArray.get(max), parent) > 0) {
                heapArray.set(parentIndex, heapArray.get(max));
                parentIndex = max;
            } else {
                break;
            }

        }

        heapArray.set(parentIndex, parent);
    }


    public boolean offer(E element) {

        if (element == null) {
            throw new NullPointerException();
        }

        heapArray.add(element);
        upHeap();
        return true;
    }

    public E poll() {

        if (heapArray.isEmpty()) {
            return null;
        }

        E root = heapArray.get(0);

        int last = heapArray.size() - 1;

        if (last > 0) {
            heapArray.set(0, heapArray.remove(last));
            downHeap();

        } else {
            heapArray.remove(0);
        }
        return root;
    }

}
