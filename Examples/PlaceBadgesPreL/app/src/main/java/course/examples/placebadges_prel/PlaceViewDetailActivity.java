package course.examples.placebadges_prel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceViewDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_view_detail);

        PlaceRecord placeRecord = new PlaceRecord(getIntent());

        ImageView mFlagImage = (ImageView) findViewById(R.id.flag_image);
        mFlagImage.setImageBitmap(placeRecord.getFlagBitmap());

        TextView mPlaceName = (TextView) findViewById(R.id.place_name);
        mPlaceName.setText(placeRecord.getPlace() + ", ");

        TextView mCountryName = (TextView) findViewById(R.id.country_name);
        mCountryName.setText(placeRecord.getCountryName());

        TextView mDate = (TextView) findViewById(R.id.date_string);
        mDate.setText("Visited on: " + placeRecord.getDate());

        TextView mLocationView = (TextView) findViewById(R.id.location);
        mLocationView.setText("Location: ("
                + placeRecord.getLocation().getLatitude() + ","
                + placeRecord.getLocation().getLongitude() + ")");

    }
}
