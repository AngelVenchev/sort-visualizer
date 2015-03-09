package org.intracode.sortvisualizer.sortalgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvenchev on 3/9/2015.
 */
public class InsertionSort implements SortAlgorithm {

    private List<SortStep> sortSteps;
    private int[] numbers;

    @Override
    public List<SortStep> SortSteps(int[] numbers) {
        this.numbers = numbers.clone();
        sortAndSaveSteps();
        return sortSteps;
    }

    private void sortAndSaveSteps() {
        sortSteps = new ArrayList<>();
        sortSteps.add(new SortStep(numbers));

        sortSteps.add(new SortStep(numbers, new int[] {0}));
        for (int i = 1; i < numbers.length; i++) {
            int next = numbers[i];
            // find the insertion location while moving all larger element up
            int j = i;
            while (j > 0 && numbers[j - 1] > numbers[j]) {
                sortSteps.add(new SortStep(numbers, new int[] {j, j-1, i}));
                swap(j,j-1);
                sortSteps.add(new SortStep(numbers, new int[] {j, j-1, i}));
                j--;
            }
            sortSteps.add(new SortStep(numbers, new int[] {i}));
        }
        sortSteps.add(new SortStep(numbers));
        sortSteps.add(new SortStep(numbers));
        sortSteps.add(new SortStep(numbers));
    }

    public void swap(int location1, int location2) {
        int swap = numbers[location1];
        numbers[location1] = numbers[location2];
        numbers[location2] = swap;
    }
}
