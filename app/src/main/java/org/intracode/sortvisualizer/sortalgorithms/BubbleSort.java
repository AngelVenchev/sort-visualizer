package org.intracode.sortvisualizer.sortalgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvenchev on 3/8/2015.
 */
public class BubbleSort implements SortAlgorithm {

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

        for(int i = 0; i < numbers.length - 1; i++) {
            for(int k = 0; k < numbers.length - i - 1; k++) {
                sortSteps.add(new SortStep(numbers, new int[] {k, k+1}));
                if(numbers[k] > numbers[k+1]) {
                    int swap = numbers[k];
                    numbers[k] = numbers[k+1];
                    numbers[k+1] = swap;
                }
                sortSteps.add(new SortStep(numbers, new int[] {k, k+1}));
            }
        }
        sortSteps.add(new SortStep(numbers));
        sortSteps.add(new SortStep(numbers));
        sortSteps.add(new SortStep(numbers));
    }
}
