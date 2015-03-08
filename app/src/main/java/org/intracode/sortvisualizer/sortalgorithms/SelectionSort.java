package org.intracode.sortvisualizer.sortalgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvenchev on 3/7/2015.
 */
public class SelectionSort implements SortAlgorithm{

    private List<SortStep> sortSteps;
    private int[] numbers;

    @Override
    public List<SortStep> SortSteps(int[] numbers) {
        this.numbers = numbers.clone();
        SortAndSaveSteps();
        return sortSteps;
    }

    private void SortAndSaveSteps() {
        sortSteps = new ArrayList<SortStep>();
        sortSteps.add(new SortStep(numbers));

        for(int i = 0; i < numbers.length - 1; i++) {
            int minIndex = i;
            for(int k = i + 1; k < numbers.length; k++) {
                sortSteps.add(new SortStep(numbers, new int[]{minIndex,k}));
                if(numbers[minIndex] > numbers[k]) {
                    minIndex = k;
                }
            }
            if(minIndex != i) {
                sortSteps.add(new SortStep(numbers, new int[]{minIndex, i}));
                int temp = numbers[minIndex];
                numbers[minIndex] = numbers[i];
                numbers[i] = temp;
                sortSteps.add(new SortStep(numbers, new int[]{minIndex, i}));
            }
        }
        sortSteps.add(new SortStep(numbers));
        sortSteps.add(new SortStep(numbers));
        sortSteps.add(new SortStep(numbers));
    }
}
