package examples.course.ticker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Handler;

public class TickerDisplayActivity extends Activity {

    private TextView mCounterView;
    private int mCounter = 0;
    private final long delay = 1000;
    private final Handler mHandler = new Handler();
    private Runnable update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker_display);

        mCounterView = (TextView) findViewById(R.id.counter);

        // Runnable that updates the counter and then posts
        // itself to the Handler to be be run again after 1 second
        update = new Runnable() {
            @Override
            public void run() {
                mCounterView.setText(String.valueOf(++mCounter));
                mHandler.postDelayed(this, delay);
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        setTimerEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimerEnabled(true);
        }

    private void setTimerEnabled(boolean isInForeground) {
        if (isInForeground) {

            // Update the counter and post the update Runnable
            mCounterView.setText(String.valueOf(mCounter));
            mHandler.postDelayed(update, delay);
        } else {

            // Remove already posted Runnables to stop the ticker's updating
            mHandler.removeCallbacks(update);
            }
    }
}
