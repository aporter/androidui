package course.examples.toast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class NotificationToastActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Set up a listener for clicks on the Button
        Button button = (Button) findViewById(R.id.toast_button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.youre_toast_string),
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }
}