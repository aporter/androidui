package course.examples.ratingsbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import course.examples.UI.RatingsBar.R;


public class RatingsBarActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final TextView tv = (TextView) findViewById(R.id.textView);

        // Get a reference to the RatingBar
        final RatingBar bar = (RatingBar) findViewById(R.id.ratingbar);

        // Set a listener for changes to RatingBar
        bar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
	
        	// Called when the user swipes the RatingBar
        	@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				tv.setText("Rating:" + rating);
			}
		});
    }
}