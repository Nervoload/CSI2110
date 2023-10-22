/* ---------------------------------------------------------------------------------
PQ1.java

Written by John Surette
StudentID: 300307306
CSI 2110
Programming Assignment 1
Date: 2023-10-21

This class implements a priority queue using an ArrayList and a Comparator.

Dependencies:

    PointSet.java       - A class containing methods to read in data from files
    LabelledPoint.java  - A class representing a point in a vector space with a label and key

------------------------------------------------------------------------------------*/

import java.util.ArrayList;
import java.util.Comparator;

public class PQ1<E> implements PriorityQueueIF<E> {

    private ArrayList<E> points;
    private Comparator<E> comparator;


    public PQ1(int capacity, Comparator<E> comparator) {
        points = new ArrayList<>(capacity);
        this.comparator = comparator;
    }

    public E peek() {
        return points.isEmpty() ? null : points.get(0);
    }

    public int size() {
        return points.size();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public boolean offer(E element) {

        if (element == null) {
            throw new NullPointerException();
        }

        points.add(element);
        int index = points.size() - 1;

        while (index > 0) {

            int parentIndex = (index - 1) / 2;

            if (comparator.compare(element, points.get(parentIndex)) <= 0) {
                break;
            }

            points.set(index, points.get(parentIndex));
            index = parentIndex;
        }
        points.set(index, element);

        return true;
    }

    public E poll() {
        if (points.isEmpty()) {
            return null;
        }

        E root = points.get(0);
        E last = points.remove(points.size() - 1);

        if (!points.isEmpty()) {

            points.set(0, last);
            int index = 0;

            // Comparing points and swapping them if necessary
            // similar to downheaping...

            while (true) {

                int left = 2 * index + 1;

                // If left index is greater than the size of the array, break
                if (left >= points.size()) {
                    break;
                }

        
                int right = left + 1;
                int swapChild = left;

                // If right index is less than the size of the array and the right index is greater than the left index, swap the child

                if (right < points.size() && comparator.compare(points.get(left), points.get(right)) < 0) {
                    swapChild = right;
                }
                if (comparator.compare(last, points.get(swapChild)) >= 0) {
                    break;
                }

                points.set(index, points.get(swapChild));
                index = swapChild;
            }
            points.set(index, last);
        }

        return root;

    }

}