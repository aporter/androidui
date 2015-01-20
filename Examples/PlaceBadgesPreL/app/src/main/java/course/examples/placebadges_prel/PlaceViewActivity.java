package course.examples.placebadges_prel;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class PlaceViewActivity extends ListActivity implements LocationListener {
    private static final long FIVE_MINS = 5 * 60 * 1000;
    private static final String TAG = "Lab-Location";

    private Location mLastLocationReading;
    private PlaceViewAdapter mAdapter;

    // default minimum time between new readings
    private final static long mMinTime = 5000;
    // default minimum distance between old and new readings.
    private final static float mMinDistance = 1000.0f;
    private LocationManager mLocationManager;

    // A fake location provider used for testing
    private MockLocationProvider mMockLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getListView().setBackgroundResource(R.drawable.sign);
        View footerView = getLayoutInflater().inflate(R.layout.footer_view,
                null);

        footerView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.i(TAG, "Entered footerView.OnClickListener.onClick()");

                if (null != mLastLocationReading) {
                    if (!mAdapter.intersects(mLastLocationReading)) {
                        Log.i(TAG, "Starting Place Download");
                        new PlaceDownloaderTask(PlaceViewActivity.this)
                                .execute(mLastLocationReading);
                    } else {
                        Log.i(TAG, "You already have this location badge");
                        Toast.makeText(getApplicationContext(),
                                "You already have this location badge",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.i(TAG, "Location data is not available");
                    Toast.makeText(getApplicationContext(),
                            "Location data is not available", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        getListView().addFooterView(footerView);

        mAdapter = new PlaceViewAdapter(this);
        setListAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mMockLocationProvider = new MockLocationProvider(
                LocationManager.NETWORK_PROVIDER, this);

        // Check NETWORK_PROVIDER for an existing location reading.
        // Only keep this last reading if it is fresh - less than 5 minutes old.

        for (String provider : mLocationManager.getAllProviders()) {

            Location tmp = mLocationManager.getLastKnownLocation(provider);

            if ((null != tmp && (System.currentTimeMillis() - tmp.getTime() < FIVE_MINS))
                    || (null != tmp && null != mLastLocationReading && tmp
                    .getAccuracy() < mLastLocationReading.getAccuracy())) {
                mLastLocationReading = tmp;
            }
        }

        // Register to receive location updates from NETWORK_PROVIDER
        for (String provider : mLocationManager.getAllProviders()) {
            mLocationManager.requestLocationUpdates(provider, mMinTime,
                    mMinDistance, this);
        }
    }

    @Override
    protected void onPause() {
        mMockLocationProvider.shutdown();
        // Unregister for location updates
        mLocationManager.removeUpdates(this);
        super.onPause();
    }

    // Callback method used by PlaceDownloaderTask
    public void addNewPlace(PlaceRecord place) {
        Log.i(TAG, "Entered addNewPlace()");
        mAdapter.add(place);
    }

    @Override
    public void onLocationChanged(Location currentLocation) {
        // Handle location updates
        if ((null == mLastLocationReading)
                || (age(currentLocation) < age(mLastLocationReading))) {
            mLastLocationReading = currentLocation;
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // not implemented
    }

    @Override
    public void onProviderEnabled(String provider) {
        // not implemented
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // not implemented
    }

    private long age(Location location) {
        return System.currentTimeMillis() - location.getTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_badges:
                showDeleteDialog();
                return true;
            case R.id.credits:
                showCreditsDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDeleteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.delete_all_dialog_string)
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_menu_delete)
                .setMessage(R.string.delete_all_dialog_string)
                .setNegativeButton(getString(R.string.no_string), null)
                .setPositiveButton(getString(R.string.yes_string),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Log.i(TAG, "deleting all records");
                                mAdapter.removeAllViews();
                            }
                        });
        dialog.show();
    }

    private void showCreditsDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.credits_string).setCancelable(true)
                .setMessage(R.string.detailed_credits_string)
                .setNeutralButton(getString(R.string.done_string), null);
        dialog.show();
    }
}
