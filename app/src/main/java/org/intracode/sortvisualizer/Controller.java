package org.intracode.sortvisualizer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;


public class Controller extends ActionBarActivity {

    Spinner topLeft;
    Spinner topRight;
    Spinner bottomLeft;
    Spinner bottomRight;
    Button reversed;
    Button nearlySorted;
    Button random;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        topLeft = (Spinner) findViewById(R.id.top_left_spinner);
        topRight = (Spinner) findViewById(R.id.top_right_spinner);
        bottomLeft = (Spinner) findViewById(R.id.bottom_left_spinner);
        bottomRight = (Spinner) findViewById(R.id.bottom_right_spinner);
        reversed = (Button) findViewById(R.id.reversed);
        nearlySorted = (Button) findViewById(R.id.nearly_sorted);
        random = (Button) findViewById(R.id.random);
        start = (Button) findViewById(R.id.start_button);

        // Perform
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
