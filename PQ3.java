/* ---------------------------------------------------------------------------------
KNN.java

Written by John Surette 
StudentID: 300307306
CSI 2110
Programming Assignment 1
Date: 2023-10-21

This class implements the base java class PriorityQueue

Dependencies:
    
    PointSet.java       - A class containing methods to read in data from files
    LabelledPoint.java  - A class representing a point in a vector space with a label and key

------------------------------------------------------------------------------------*/

import java.util.Comparator;

public class PQ3<E> implements PriorityQueueIF<E> {
    private java.util.PriorityQueue<E> queue;

    public PQ3(int capacity, Comparator<E> comparator) {

        queue = new java.util.PriorityQueue<>(capacity, comparator);
    }

    // basic implementation for peek using the java.util.PriorityQueue

    public E peek() {
        return queue.peek();
    }

    // basic implementation for size using the java.util.PriorityQueue
    public int size() {
        return queue.size();
    }

    // basic implementation for isEmpty using the java.util.PriorityQueue
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // basic implementation for offer using the java.util.PriorityQueue
    public boolean offer(E element) {
        return queue.offer(element);
    }

    // basic implementation for poll using the java.util.PriorityQueue
    public E poll() {
        return queue.poll();
    }

}