package org.intracode.sortvisualizer.connectionMachine.transformator;

import org.intracode.sortvisualizer.sortalgorithms.SortStep;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvenchev on 3/8/2015.
 */
public class ArrayTransformator {
    private final static int NUMBER_OF_ROWS_PER_DISPLAY = 12;
    private final static byte MIDDLE_LED_VALUE = (byte)128;
    private final static byte MAX_LED_VALUE = (byte)255;


    /**
     * a collection of frames to be shown on the Connection Machine
     * for more info see {@linktourl http://www.teco.kit.edu/cm/dev/}
     * All parameters are expected to have number collections with exact length of 12
     * @param topLeft the SortSteps for the top left sort display
     * @param topRight the SortSteps for the top right sort display
     * @param bottomLeft the SortSteps for the bottom left sort display
     * @param bottomRight the SortSteps for the bottom right sort display
     * @return an array of arrays of bytes to be transmitted to the Connection Machine as frames
     */
    public byte[][][] GetFramesAsByteArray(List<SortStep> topLeft,
                                           List<SortStep> topRight,
                                           List<SortStep> bottomLeft,
                                           List<SortStep> bottomRight) {
        // TODO: Implement
        throw new UnsupportedOperationException();
    }

    public byte[][][] ConvertSingleSortVisualization(List<SortStep> sortSteps) {
        byte[][][] visual = new byte[sortSteps.size()][NUMBER_OF_ROWS_PER_DISPLAY][NUMBER_OF_ROWS_PER_DISPLAY];

        for(int frameIndex = 0; frameIndex < sortSteps.size(); frameIndex++) {
            for(int x = 0; x < sortSteps.get(frameIndex).numbers.length; x++) {
                byte lightValue = MIDDLE_LED_VALUE;
                if(sortSteps.get(frameIndex).numberHighlighters[x]) {
                    lightValue = MAX_LED_VALUE;
                }
                for(int y = 0; y < sortSteps.get(frameIndex).numbers[x]; y++) {
                    visual[frameIndex][x][y] = lightValue;
                }
            }
        }
        return visual;
    }

    public byte[][] transformTwoDimensionArrayCollectionToSingleDimension(byte[][][] frameCollection) {
        byte[][] singleDimensionCollection = new byte[frameCollection.length][NUMBER_OF_ROWS_PER_DISPLAY];
        for(int i = 0; i < frameCollection.length;i++) {
            singleDimensionCollection[i] = transformTwoDimensionArrayToSingleDimension(frameCollection[i]);
        }
        return singleDimensionCollection;
    }

    private byte[] transformTwoDimensionArrayToSingleDimension(byte[][] frame) {
        byte[] singleDimensionFrame = new byte[NUMBER_OF_ROWS_PER_DISPLAY * NUMBER_OF_ROWS_PER_DISPLAY];
        for(int i =0; i < NUMBER_OF_ROWS_PER_DISPLAY; i++) {
            for(int k = 0; k < NUMBER_OF_ROWS_PER_DISPLAY; k++) {
                singleDimensionFrame[(frame[i].length - 1 - k) * frame.length + i] = frame[i][k];
            }
        }
        return singleDimensionFrame;
    }
}
