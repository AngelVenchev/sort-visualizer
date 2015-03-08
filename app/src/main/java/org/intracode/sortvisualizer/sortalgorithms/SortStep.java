package org.intracode.sortvisualizer.sortalgorithms;

/**
 * Created by angelvenchev on 3/7/2015.
 */
public class SortStep {
    public int[] numbers;
    public boolean[] numberHighlighters;

    /**
     * Sets the numbers in the step and highlights all of the indexes in the highlighted array
     * @param numbers the numbers to be set
     * @param highlighted the indexes to be highlighted
     */
    public SortStep(int[] numbers, int[] highlighted) {
        this.numbers = numbers.clone();
        highlightNumbers(numbers, highlighted);
    }

    /**
     * Sets the numbers in the step and highlights all numbers
     *
     * @param numbers the numbers to be set
     */
    public SortStep(int[] numbers) {
        int[] highlighted = new int[numbers.length];
        for(int i = 0; i < highlighted.length; i++) {
            highlighted[i] = i;
        }
        this.numbers = numbers.clone();
        highlightNumbers(numbers, highlighted);
    }

    private void highlightNumbers(int[] numbers, int[] highlighted) {
        numberHighlighters = new boolean[numbers.length];
        for(int highlighter : highlighted) {
            if(highlighter < numbers.length)
                numberHighlighters[highlighter] = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(int i = 0; i < numbers.length - 1; i++) {
            builder.append(numbers[i]);
            builder.append(", ");
        }
        builder.append(numbers[numbers.length - 1]);
        builder.append("]\n[");
        for(int i = 0; i < numberHighlighters.length - 1; i++) {
            if(numberHighlighters[i]) {
                builder.append("^, ");
            }
            else {
                builder.append(" , ");
            }
        }
        if(numberHighlighters[numberHighlighters.length - 1]) {
            builder.append("^]");
        }
        else {
            builder.append(" ]");
        }
        return builder.toString();
    }

}
