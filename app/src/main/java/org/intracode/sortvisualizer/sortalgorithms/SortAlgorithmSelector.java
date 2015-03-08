package org.intracode.sortvisualizer.sortalgorithms;

/**
 * Created by angelvenchev on 3/8/2015.
 */
public class SortAlgorithmSelector {
    public static SortAlgorithm getSortAlgorithm(String algorithmName) {
        if(algorithmName.compareTo("Selection Sort") == 0) {
            return new SelectionSort();
        } else if(algorithmName.compareTo("Bubble Sort") == 0) {
            return new BubbleSort();
        } else if(algorithmName.compareTo("Insertion Sort") == 0) {
            throw new UnsupportedOperationException();
        } else if(algorithmName.compareTo("Merge Sort") == 0) {
            throw new UnsupportedOperationException();
        } else if(algorithmName.compareTo("Quick Sort") == 0) {
            throw new UnsupportedOperationException();
        } else {
            return new SelectionSort();
        }
    }
}
