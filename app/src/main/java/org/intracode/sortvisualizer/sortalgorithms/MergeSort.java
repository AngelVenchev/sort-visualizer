package org.intracode.sortvisualizer.sortalgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvenchev on 3/8/2015.
 */
public class MergeSort implements SortAlgorithm {

    private int[] numbers;
    private List<SortStep> sortSteps;

    @Override
    public List<SortStep> SortSteps(int[] numbers) {
        this.numbers = numbers.clone();
        sortSteps = new ArrayList<>();
        sortAndSaveSteps(0, this.numbers.length - 1);
        return sortSteps;
    }

    /**
     * The actual merge sort algorithm.
     * @param min The minimum index to be sorted.
     * @param max The maximum index to be sorted.
     */
    void sortAndSaveSteps(int min, int max){

        addNumbersInRange(min, max);

        if(max  - min == 0){//only one element.
            // Do nothing
        }
        else if(max - min == 1){//only two elements and swaps them
            if(numbers[min]>numbers[max]) {
                swap(min, max);
                sortSteps.add(new SortStep(numbers, new int[] {min, max}));
            }
        }
        else{
            int mid=(int) Math.floor((min + max) / 2);//The midpoint
            sortSteps.add(new SortStep(numbers, new int[] { mid }));

            sortAndSaveSteps(min, mid);//sort the left side
            sortAndSaveSteps(mid + 1, max);//sort the right side
            merge(min, max, mid);//combines them
        }
    }

    private void addNumbersInRange(int min, int max) {
        int[] highlighted = new int[max - min + 1];
        for(int i = min; i < highlighted.length; i++) {
            highlighted[i - min] = i;
        }
        sortSteps.add(new SortStep(numbers,highlighted));
    }

    /**
     * The merge method combines the two sorted portions of the game.
     * @param min The minimum index to be merged.
     * @param max The maximum index to be merged.
     * @param mid The mid point in the section of the array to be merged. It's also the last index of the left portion of the array
     * and mid+1 is the first index in the right portion.
     */
    void merge(int min,int max,int mid){
        int i = min;
        while(i <= mid){
            if(numbers[i] > numbers[mid + 1]){
                sortSteps.add(new SortStep(numbers, new int[] {i, mid + 1}));
                swap(i, mid + 1);
                sortSteps.add(new SortStep(numbers, new int[] {i, mid + 1}));
                push(mid + 1,max);
            }
            i++;
        }
    }
    /**
     * Swaps two elements in the given array.
     * @param loc1 The index of an integer to swap.
     * @param loc2 The index of an integer to swap.
     */
    void swap(int loc1,int loc2){
        int swap = numbers[loc1];
        numbers[loc1] = numbers[loc2];
        numbers[loc2] = swap;
    }

    /**
     * Puts the largest value at the end of the array. Used in the merge method after a swap of sorted array portions. An example
     * would be {5,6,7,8,1,2,3,4} left {5,6,7,8} and right {1,2,3,4} and 1<5 so they will swap. Left {1,6,7,8} and right {5,2,3,4}
     * and push will allow it to be {1,6,7,8} left and {2,3,4,5} right.
     * @param s The start index of the push.
     * @param e The end index of the push.
     */
    void push(int s,int e){
        for(int i = s; i < e; i++){
            if(numbers[i] > numbers[i + 1])
                sortSteps.add(new SortStep(numbers, new int[] {i, i + 1}));
                swap(i, i + 1);
                sortSteps.add(new SortStep(numbers, new int[] {i, i + 1}));
        }
    }


}
