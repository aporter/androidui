package course.examples.placebadges;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Outline;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;


public class PlaceViewActivity extends Activity implements LocationListener {

    @SuppressWarnings("unused")
    private static final String TAG = "PlaceViewActivity";
    private static final long FIVE_MINS = 5 * 60 * 1000;
    private static final int DELETE_ID = 0;
    private static final int CREDITS_ID = 1;

    private PlaceViewAdapter mAdapter;
    private LocationManager mLocationManager;

    // A fake location provider used for testing
    private MockLocationProvider mMockLocationProvider;
    private Location mLastLocationReading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allow Transition on return to this Activity
        getWindow().setAllowReturnTransitionOverlap(true);

        setContentView(course.examples.placebadges.R.layout.place_activity);

        // Style the Floating Action Button
        styleFAB();

        //Set up RecyclerView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.badge_view_card);

        // Set the LayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Create the Adapter
        mAdapter = new PlaceViewAdapter(new ArrayList<PlaceRecord>(),
                course.examples.placebadges.R.layout.place_badge_view,
                this);

        // Set the adapter on the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // Get reference to LocationManager
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    private void styleFAB() {

        // Create circular outline
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int diameter = getResources().getDimensionPixelSize(R.dimen.add_button_diameter);
                outline.setOval(0, 0, diameter, diameter);
            }
        };

        // Set outline on Floating Action Button
        ImageButton floatingButton = (ImageButton) findViewById(R.id.add_button);
        floatingButton.setOutlineProvider(viewOutlineProvider);

        // Add OnClickListener to Floating Action Button
        floatingButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Determine whether or not location data is available
                if (null == mLastLocationReading) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.no_location_data_string), Toast.LENGTH_LONG)
                            .show();
                } else {
                    // Check whether user already has a PlaceBadge for this location
                    if (mAdapter.intersects(mLastLocationReading)) {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.duplicate_place_string),
                                Toast.LENGTH_LONG).show();
                    } else {
                        // Start an AsyncTask to download PlaceBadge and place information
                        new PlaceDownloaderTask(PlaceViewActivity.this)
                                .execute(mLastLocationReading);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Create MockLocationProvider used for testing
        mMockLocationProvider = new MockLocationProvider(
                LocationManager.NETWORK_PROVIDER, this);

        // Check all Providers for a recent location reading
        for (String provider : mLocationManager.getAllProviders()) {
            Location tmp = mLocationManager.getLastKnownLocation(provider);
            if ((null != tmp && (System.currentTimeMillis() - tmp.getTime() < FIVE_MINS))
                    || (null != tmp && null != mLastLocationReading && tmp
                    .getAccuracy() < mLastLocationReading.getAccuracy())) {
                mLastLocationReading = tmp;
            }
        }

        // Register to receive location updates from all providers
        for (String provider : mLocationManager.getAllProviders()) {
            long mMinTime = 5000;
            float mMinDistance = 1000.0f;
            mLocationManager.requestLocationUpdates(provider, mMinTime,
                    mMinDistance, this);
        }
    }

    @Override
    protected void onPause() {
        mMockLocationProvider.shutdown();
        mLocationManager.removeUpdates(this);
        super.onPause();
    }

    // Callback method used to display newly acquired PlaceBadge
    public void addNewPlace(PlaceRecord place) {
        mAdapter.add(place);
    }

    // Return the age of this Location
    private long age(Location location) {
        return System.currentTimeMillis() - location.getTime();
    }

    /* Implement LocationListener interface */

    @Override
    public void onLocationChanged(Location currentLocation) {
        if ((null == mLastLocationReading) || (age(currentLocation) < age(mLastLocationReading))) {
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

    /* Handle Menu interaction */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(course.examples.placebadges.R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_badges:
                show(DELETE_ID);
                return true;
            case R.id.credits:
                show(CREDITS_ID);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void show(int dialogID) {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(dialogID);
        newFragment.show(getFragmentManager(), null);
    }

    /*
     * Remove all PlaceBadges
     */
    private void removeAllPlaceBadges() {
        mAdapter.removeAllPlaceBadges();
    }

    /* Implement custom AlertDialog */
    public static class MyAlertDialogFragment extends DialogFragment {
        public static final String OP_ID = "ID";

        public static MyAlertDialogFragment newInstance(int dialogID) {
            MyAlertDialogFragment dialogFragment = new MyAlertDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(OP_ID, dialogID);
            dialogFragment.setArguments(bundle);
            return dialogFragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Inflate the dialog
            LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View dialogView = null;

            switch (getArguments().getInt(OP_ID)) {

                case DELETE_ID: {
                    dialogView = layoutInflater.inflate(R.layout.delete_confirm_layout, null);

                    // Attach listener to "No" Button
                    Button noButton = (Button) dialogView
                            .findViewById(R.id.no_button);
                    noButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismiss();
                        }
                    });

                    // Attach listener to "Yes" Button
                    Button yesButton = (Button) dialogView
                            .findViewById(R.id.yes_button);
                    yesButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((PlaceViewActivity) getActivity()).removeAllPlaceBadges();
                            dismiss();
                        }
                    });

                    break;
                }

                case CREDITS_ID: {
                    dialogView = layoutInflater.inflate(R.layout.credits_layout, null);

                    // Attach listener to "Done" Button
                    Button doneButton = (Button) dialogView
                            .findViewById(R.id.done_button);
                    doneButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismiss();
                        }
                    });

                }
            }

            if (null != dialogView) {

                // Create the AlertDialog
                return new AlertDialog.Builder(getActivity()).setView(dialogView)
                        .create();
            } else {
                return super.onCreateDialog(savedInstanceState);
            }
        }
    }
}
