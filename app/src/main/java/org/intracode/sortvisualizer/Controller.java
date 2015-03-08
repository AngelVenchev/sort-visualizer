package org.intracode.sortvisualizer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import org.intracode.sortvisualizer.connectionMachine.communication.DisplayWriter;
import org.intracode.sortvisualizer.connectionMachine.transformator.ArrayTransformer;
import org.intracode.sortvisualizer.sortalgorithms.SelectionSort;
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
    Button reversed;
    Button nearlySorted;
    Button random;
    Button start;

    SortAlgorithm topLeftSort;
    SortAlgorithm topRightSort;
    SortAlgorithm bottomLeftSort;
    SortAlgorithm bottomRightSort;
    int[] numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        reversed = (Button) findViewById(R.id.reversed);
        nearlySorted = (Button) findViewById(R.id.nearly_sorted);
        random = (Button) findViewById(R.id.random);
        start = (Button) findViewById(R.id.start_button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            topLeft = (Spinner) findViewById(R.id.top_left_spinner);
            topRight = (Spinner) findViewById(R.id.top_right_spinner);
            bottomLeft = (Spinner) findViewById(R.id.bottom_left_spinner);
            bottomRight = (Spinner) findViewById(R.id.bottom_right_spinner);

            // Get sorts
            topLeftSort = SortAlgorithmSelector.getSortAlgorithm(topLeft.getSelectedItem().toString());
            topRightSort = SortAlgorithmSelector.getSortAlgorithm(topRight.getSelectedItem().toString());
            bottomLeftSort = SortAlgorithmSelector.getSortAlgorithm(bottomLeft.getSelectedItem().toString());
            bottomRightSort = SortAlgorithmSelector.getSortAlgorithm(bottomRight.getSelectedItem().toString());

            SortAlgorithm[] sorts = new SortAlgorithm[] { topLeftSort, topRightSort, bottomLeftSort, bottomRightSort };

            // Run sorts
            ArrayList<List<SortStep>> stepsCollection = new ArrayList<List<SortStep>>();
            for(int i = 0; i < sorts.length; i++) {
                stepsCollection.add(sorts[i].SortSteps(new int[]{ 8,12,6,5,3,2,11,4,9,10,1,7 }));
            }

            // Transform frames into transmittable messages
            ArrayTransformer transformer = new ArrayTransformer();
            byte[][] readyFrames = transformer.GetFramesAsByteArrays(stepsCollection);

            // Transmit to CM
            DisplayWriter writer = new DisplayWriter();
            writer.Write(readyFrames, Controller.this);
            }
        });
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
