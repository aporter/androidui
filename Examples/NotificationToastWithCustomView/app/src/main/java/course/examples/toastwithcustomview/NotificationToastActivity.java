package course.examples.toastwithcustomview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class NotificationToastActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Set up listener for clicks on the Button
        Button button = (Button) findViewById(R.id.toast_button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);

                // Inflate custom Toast View
                View toastLayout = getLayoutInflater().inflate(
                        R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                // Set the inflated View on the Toast
                toast.setView(toastLayout);
                toast.show();
            }
        });

    }
}