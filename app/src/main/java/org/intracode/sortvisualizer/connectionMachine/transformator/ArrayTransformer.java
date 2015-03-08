package org.intracode.sortvisualizer.connectionMachine.transformator;

import org.intracode.sortvisualizer.sortalgorithms.SortStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvenchev on 3/8/2015.
 */
public class ArrayTransformer {
    private final static int ROWS_PER_DISPLAY = 12;
    private final static int ALL_ROWS = 24;
    private final static byte MIDDLE_LED_VALUE = (byte)128;
    private final static byte MAX_LED_VALUE = (byte)255;

    /**
     * a collection of frames to be shown on the Connection Machine
     * for more info see {@linktourl http://www.teco.kit.edu/cm/dev/}
     * All parameters are expected to have number collections with exact length of 12
     * @param collectionOfFrames the collection of all SortSteps for all of the displays
     * @return an array of arrays of bytes to be transmitted to the Connection Machine as frames
     */
    public byte[][] GetFramesAsByteArrays(ArrayList<List<SortStep>> collectionOfFrames) {
        byte[][][] topLeftBytes = ConvertSingleSortVisualization(collectionOfFrames.get(0));
        byte[][][] topRightBytes = ConvertSingleSortVisualization(collectionOfFrames.get(1));
        byte[][][] bottomLeftBytes = ConvertSingleSortVisualization(collectionOfFrames.get(2));
        byte[][][] bottomRightBytes = ConvertSingleSortVisualization(collectionOfFrames.get(3));

        byte[][][] combinedPicture;
        combinedPicture = combineAllScreens(topLeftBytes, topRightBytes, bottomLeftBytes, bottomRightBytes);

        return transformTwoDimensionArrayCollectionToSingleDimension(combinedPicture);
    }

    private byte[][][] ConvertSingleSortVisualization(List<SortStep> sortSteps) {
        byte[][][] visual = new byte[sortSteps.size()][ROWS_PER_DISPLAY][ROWS_PER_DISPLAY];

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

    private byte[][] transformTwoDimensionArrayCollectionToSingleDimension(byte[][][] frameCollection) {
        byte[][] singleDimensionCollection = new byte[frameCollection.length][ALL_ROWS * ALL_ROWS];
        for(int i = 0; i < frameCollection.length;i++) {
            singleDimensionCollection[i] = transformTwoDimensionArrayToSingleDimension(frameCollection[i]);
        }
        return singleDimensionCollection;
    }

    private byte[] transformTwoDimensionArrayToSingleDimension(byte[][] frame) {
        byte[] singleDimensionFrame = new byte[ALL_ROWS * ALL_ROWS];
        for(int i =0; i < ALL_ROWS; i++) {
            for(int k = 0; k < ALL_ROWS; k++) {
                singleDimensionFrame[(frame[i].length - 1 - k) * frame.length + i] = frame[i][k];
            }
        }
        return singleDimensionFrame;
    }

    private byte[][][] combineAllScreens(byte[][][] frameCollection1,
                                         byte[][][] frameCollection2,
                                         byte[][][] frameCollection3,
                                         byte[][][] frameCollection4) {
        int length1 = Math.max(frameCollection1.length, frameCollection2.length);
        int length2 = Math.max(frameCollection3.length, frameCollection4.length);
        int numberOfFrames = Math.max(length1, length2);
        byte[][][] combinedScreens = new byte[numberOfFrames][ALL_ROWS][ALL_ROWS];

        byte[][][][] collections = new byte[][][][] {frameCollection3, frameCollection1, frameCollection4, frameCollection2};

        for(int frameIndex = 0; frameIndex < numberOfFrames; frameIndex++) {
            int[] currentFrame = new int[]{frameIndex, frameIndex, frameIndex, frameIndex};
            for (int i = 0; i < collections.length; i++) {
                if (frameIndex >= collections[i].length) {
                    currentFrame[i] = collections[i].length - 1;
                }
            }

            for (int i = 0; i < 2; i++) {
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < ROWS_PER_DISPLAY; j++) {
                        for (int l = 0; l < ROWS_PER_DISPLAY; l++) {
                            combinedScreens[frameIndex][i * ROWS_PER_DISPLAY + j][k * ROWS_PER_DISPLAY + l] =
                                    collections[i * 2 + k][currentFrame[i * 2 + k]][j][l];
                        }
                    }
                }
            }
        }
        return combinedScreens;
    }
}
