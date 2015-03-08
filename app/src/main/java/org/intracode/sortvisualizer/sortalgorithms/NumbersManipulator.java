package org.intracode.sortvisualizer.sortalgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by angelvenchev on 3/8/2015.
 */
public class NumbersManipulator {
    private static final int MAX_NUMBERS_PER_SORT = 12;
    private static final int[] REVERSED_ORDER = new int[] { 12,11,10,9,8,7,6,5,4,3,2,1 };
    private static final int[] ORDERED = new int[] { 1,2,3,4,5,6,7,8,9,10,11,12 };

    public static int[] getRandomNumbers() {
        ArrayList<Integer> someNumbers = new ArrayList<>();
        for(int i = 0; i < MAX_NUMBERS_PER_SORT; i++) {
            someNumbers.add(i+1);
        }

        Collections.shuffle(someNumbers);

        int[] randomNumbers = new int[MAX_NUMBERS_PER_SORT];
        for(int i = 0; i < MAX_NUMBERS_PER_SORT; i++) {
            randomNumbers[i] = someNumbers.get(i);
        }

        return randomNumbers;
    }

    public static int[] getReversedNumbers() {
        return REVERSED_ORDER;
    }

    public static int[] getNearlySortedNumbers() {
        int[] nearlyOrdered = ORDERED.clone();

        final int batchCount = 4;

        for(int i = 0; i <  batchCount; i++) {
            permuteBatch(nearlyOrdered, i * (MAX_NUMBERS_PER_SORT /  batchCount), (i+1) * (MAX_NUMBERS_PER_SORT /  batchCount));
        }

        return nearlyOrdered;
    }

    private static void permuteBatch(int[] collection, int startIndex, int endIndex) {
        Random generator = new Random();
        int firstIndex = generator.nextInt(endIndex - startIndex) + startIndex;
        int secondIndex = generator.nextInt(endIndex - startIndex) + startIndex;
        int temp = collection[firstIndex];
        collection[firstIndex] = collection[secondIndex];
        collection[secondIndex] = temp;
    }
}
