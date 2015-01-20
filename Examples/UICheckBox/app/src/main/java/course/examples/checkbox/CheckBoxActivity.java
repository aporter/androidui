package course.examples.checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class CheckBoxActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Get a reference to the CheckBox
        final CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);

        // Set an OnClickListener on the CheckBox
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Check whether CheckBox is currently checked
                // Set CheckBox text accordingly
                if (isChecked) {
                    checkbox.setText(getString(R.string.im_checked));
                } else {
                    checkbox.setText(getString(R.string.im_not_checked));
                }
            }
        });

                // Get a reference to the Hide CheckBox Button
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toggle the CheckBox's visibility state
                // Set the Button text accordingly
                if (checkbox.isShown()) {
                    checkbox.setVisibility(View.INVISIBLE);
                    button.setText(getString(R.string.unhide_checkbox));
                } else {
                    checkbox.setVisibility(View.VISIBLE);
                    button.setText(getString(R.string.hide_checkbox));
                }
            }
        });
    }
}