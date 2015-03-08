package org.intracode.sortvisualizer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import org.intracode.sortvisualizer.connectionMachine.communication.DisplayWriter;
import org.intracode.sortvisualizer.connectionMachine.transformator.ArrayTransformer;
import org.intracode.sortvisualizer.sortalgorithms.NumbersManipulator;
import org.intracode.sortvisualizer.sortalgorithms.SortAlgorithm;
import org.intracode.sortvisualizer.sortalgorithms.SortAlgorithmSelector;
import org.intracode.sortvisualizer.sortalgorithms.SortStep;

import java.util.ArrayList;
import java.util.List;


public class Controller extends ActionBarActivity {

    Spinner topLeft;
    Spinner topRight;
    Spinner bottomLeft;
    Spinner bottomRight;

    SortAlgorithm topLeftSort;
    SortAlgorithm topRightSort;
    SortAlgorithm bottomLeftSort;
    SortAlgorithm bottomRightSort;
    int[] numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
    }

    public void onClickStart(View v) {
        SortAlgorithm[] sorts = getSortAlgorithms();

        // Run sorts
        ArrayList<List<SortStep>> stepsCollection = getStepsCollection(sorts);

        // Transform frames into transmittable messages
        ArrayTransformer transformer = new ArrayTransformer();
        byte[][] readyFrames = transformer.GetFramesAsByteArrays(stepsCollection);

        // Transmit to CM
        DisplayWriter writer = new DisplayWriter();
        writer.write(readyFrames, Controller.this);
    }

    private ArrayList<List<SortStep>> getStepsCollection(SortAlgorithm[] sorts) {
        ArrayList<List<SortStep>> stepsCollection = new ArrayList<List<SortStep>>();
        for(int i = 0; i < sorts.length; i++) {
            stepsCollection.add(sorts[i].SortSteps(numbers));
        }
        return stepsCollection;
    }

    private SortAlgorithm[] getSortAlgorithms() {
        topLeft = (Spinner) findViewById(R.id.top_left_spinner);
        topRight = (Spinner) findViewById(R.id.top_right_spinner);
        bottomLeft = (Spinner) findViewById(R.id.bottom_left_spinner);
        bottomRight = (Spinner) findViewById(R.id.bottom_right_spinner);

        // Get sorts
        topLeftSort = SortAlgorithmSelector.getSortAlgorithm(topLeft.getSelectedItem().toString());
        topRightSort = SortAlgorithmSelector.getSortAlgorithm(topRight.getSelectedItem().toString());
        bottomLeftSort = SortAlgorithmSelector.getSortAlgorithm(bottomLeft.getSelectedItem().toString());
        bottomRightSort = SortAlgorithmSelector.getSortAlgorithm(bottomRight.getSelectedItem().toString());

        return new SortAlgorithm[] { topLeftSort, topRightSort, bottomLeftSort, bottomRightSort };
    }

    public void onClickSetNumbers(View v) {
        switch (v.getId()) {
            case R.id.nearly_sorted:
                numbers = NumbersManipulator.getNearlySortedNumbers();
                break;
            case R.id.random:
                numbers = NumbersManipulator.getRandomNumbers();
                break;
            case R.id.reversed:
                numbers = NumbersManipulator.getReversedNumbers();
                break;
        }
        displaySetup();
    }

    // Create a step for every display and repeat it multiple times.
    public void displaySetup() {
        SortStep step = new SortStep(numbers);
        ArrayList<List<SortStep>> frames = new ArrayList<>();
        final int temporaryFrameCount = 10;
        final int numberOfDisplays = 4;
        for(int i = 0; i < numberOfDisplays; i++) {
            ArrayList<SortStep> oneDisplay = new ArrayList<>();
            for(int k = 0; k < temporaryFrameCount; k++) {
                oneDisplay.add(step);
            }
            frames.add(oneDisplay);
        }
        ArrayTransformer transformer = new ArrayTransformer();
        byte[][] framesAsBytes = transformer.GetFramesAsByteArrays(frames);

        DisplayWriter writer = new DisplayWriter();
        writer.write(framesAsBytes, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
