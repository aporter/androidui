package course.examples.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerActivity extends Activity implements OnItemSelectedListener {

    private boolean mFirstTime = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Get a reference to the Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Create an Adapter that holds a list of colors
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.colors,
                R.layout.dropdown_item);


        // Set the Adapter for the spinner
        spinner.setAdapter(adapter);

        // Set an setOnItemSelectedListener on the spinner
        spinner.setOnItemSelectedListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        if (!mFirstTime) {
            // Display a Toast message indicating the currently selected item
            Toast.makeText(
                    parent.getContext(),
                    "The color is " + parent.getItemAtPosition(pos),
                    Toast.LENGTH_LONG).show();
        } else {
            mFirstTime = false;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Not implemented
    }
}