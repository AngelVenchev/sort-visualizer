package org.intracode.sortvisualizer.sortalgorithms;

import java.util.List;

/**
 * Created by angelvenchev on 3/7/2015.
 */
public interface SortAlgorithm {


    public List<Tuple<int[], boolean[]>> SortSteps(Integer[] numbers);


}
