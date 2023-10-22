/* ---------------------------------------------------------------------------------
KNN.java

Written by John Surette
StudentID: 300307306
CSI 2110
Programming Assignment 1
Date: 2023-10-21

This program implements the K-Nearest Neighbors algorithm using three different
PriorityQueue implementations. The program takes in four command line arguments:
    1. The version of the PriorityQueue to use (1, 2, or 3)
    2. The number of nearest neighbors to find
    3. The name of the query file
    4. The name of the base file

Dependencies:
    PQ1.java - A PriorityQueueIF implementation using an unsorted ArrayList
    PQ2.java - A PriorityQueueIF implementation using an ArrayList with a Heap
    PQ3.java - A PriorityQueueIF Implementation using a PriorityQueue
    PointSet.java       - A class containing methods to read in data from files
    LabelledPoint.java  - A class representing a point in a vector space with a label and key

------------------------------------------------------------------------------------*/

import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class KNN {

    // The generateNNQueue method takes in a version number and a k value and returns a new PriorityQueue
    
    private static PriorityQueueIF<LabelledPoint> generateNNQueue(int version, int k) {

        // Switch statement to determine which PriorityQueue to return based on the version number

        switch (version) {
            case 1:
                return new PQ1<>(k, (a, b) -> Double.compare(b.getKey(), a.getKey()));
            case 2:
                return new PQ2<>(k, (a, b) -> Double.compare(b.getKey(), a.getKey()));
            case 3:
                return new PQ3<>(k, (a, b) -> Double.compare(b.getKey(), a.getKey()));
            default:
                throw new IllegalArgumentException("Invalid version. Supported versions are 1, 2, and 3.");
        }
    }

    // The main method takes in four command line arguments and runs the KNN algorithm

    public static void main(String[] args) {


        if (args.length != 4) {

            System.out.println("Usage: java KNN <version> <k> <baseFilename> <queryFilename> ");
            return;

        }

        int version = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        
        String baseFilename = args[2];
        String queryFilename = args[3];
        

        // Read dataset and query points from files
        
        ArrayList<LabelledPoint> querySet = PointSet.read_ANN_SIFT(queryFilename);
        ArrayList<LabelledPoint> baseSet = PointSet.read_ANN_SIFT(baseFilename);


        // Run the KNN algorithm and save the results to a file
        
        try {

            String fileName = "KNN_" + version + "_" + k + "_" + querySet.size() + "_" + baseSet.size() + ".txt";
            // Using FileWriter and BufferedWriter to write to a file specifying test, k value, query set size, and baseSet size
            
            FileWriter output = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(output);

            long startTime = System.currentTimeMillis(); // Record the start time

            // Iterate through each query point and find the k nearest neighbors for each point in the baseSet

            for (LabelledPoint queryPoint : querySet) {
                PriorityQueueIF<LabelledPoint> neighbours = generateNNQueue(version, k);        // call to the generateNNQueue method to create a new PriorityQueue
            
            // Iterate through each point in the baseSet and calculate the distance to the query point
            
            // Print current query point
                System.out.println("Query Point: " + queryPoint.getLabel());

                for (LabelledPoint baseSetPoint : baseSet) {
                    double distance = queryPoint.distanceTo(baseSetPoint);
                    baseSetPoint.setKey(distance);

                    if (neighbours.size() < k) {

                        neighbours.offer(baseSetPoint);

                    } else {

                        if (neighbours.peek().getKey() > distance) {
                            neighbours.poll();
                            neighbours.offer(baseSetPoint);
                        }
                    }
                }
                
                // Write the results to the file

                writer.write(queryPoint.getLabel() + " : ");

                while (!neighbours.isEmpty()) {

                    LabelledPoint neighbour = neighbours.poll();
                    writer.write(neighbour.getLabel() + ", " + neighbour.getKey() + ", ");
                }
                writer.write("\n");

                // Notify success of query point search
                System.out.println("Query Point NN for " + queryPoint.getLabel() + " found.");
            }

            writer.close();

            long endTime = System.currentTimeMillis(); // Record the end time

            System.out.println("Execution time for " + querySet.size() + " query points and " + baseSet.size() + " base points: " + (endTime - startTime) + " milliseconds for version " + version + " with k = " + k + ".");
            System.out.println("Saved Results to " + fileName + ".\n" );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
