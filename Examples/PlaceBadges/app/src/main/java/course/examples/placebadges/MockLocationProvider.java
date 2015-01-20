package course.examples.placebadges;

// Adapted from code found at: 
// http://mobiarch.wordpress.com/2012/07/17/testing-with-mock-location-data-in-android/

import android.content.Context;
import android.location.LocationManager;

class MockLocationProvider {

    private final String mProviderName;
    private final LocationManager mLocationManager;

    private static final int MOCK_ACCURACY = 5;

    public MockLocationProvider(String name, Context ctx) {
        mProviderName = name;
        mLocationManager = (LocationManager) ctx
                .getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.addTestProvider(mProviderName, false, false, false,
                false, true, true, true, 0, MOCK_ACCURACY);
        mLocationManager.setTestProviderEnabled(mProviderName, true);
    }

    public void shutdown() {
        mLocationManager.removeTestProvider(mProviderName);
    }
}
