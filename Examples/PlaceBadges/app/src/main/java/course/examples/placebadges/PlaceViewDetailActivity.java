package course.examples.placebadges;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class PlaceViewDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allow an EnterTransition
        getWindow().setAllowEnterTransitionOverlap(true);

        // Create a PlaceRecord from the Intent used to start this Activity
        PlaceRecord placeRecord = new PlaceRecord(getIntent());

        setContentView(course.examples.placebadges.R.layout.place_detail_view);

        ImageView mFlagImage = (ImageView) findViewById(R.id.flag_image);
        mFlagImage.setImageBitmap(placeRecord.getFlagBitmap());

        TextView mPlaceName = (TextView) findViewById(R.id.place_name);
        mPlaceName.setText(placeRecord.getPlace() + ", ");

        TextView mCountryName = (TextView) findViewById(R.id.country_name);
        mCountryName.setText(placeRecord.getCountryName());

        TextView mDate = (TextView) findViewById(R.id.date_string);
        mDate.setText("Visited on: " + placeRecord.getDate());

        TextView mLocationView = (TextView) findViewById(R.id.location);
        mLocationView.setText("Location: (" + placeRecord.getLocation().getLatitude()
                + ","
                + placeRecord.getLocation().getLongitude()
                + ")");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
