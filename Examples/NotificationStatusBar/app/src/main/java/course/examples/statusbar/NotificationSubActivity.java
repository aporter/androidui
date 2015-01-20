package course.examples.statusbar;

import android.app.Activity;
import android.os.Bundle;

// Special Activity used as an entry point when the
// user clicks on a notification
public class NotificationSubActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity);
    }
}
