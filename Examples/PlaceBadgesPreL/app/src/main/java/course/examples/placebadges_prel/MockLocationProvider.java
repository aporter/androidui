package course.examples.placebadges_prel;

// Adapted from code found at: 
// http://mobiarch.wordpress.com/2012/07/17/testing-with-mock-location-data-in-android/

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;

class MockLocationProvider {

    private final String mProviderName;
    private final LocationManager mLocationManager;

    private static final float mockAccuracy = 5;

    public MockLocationProvider(String name, Context ctx) {
        this.mProviderName = name;

        mLocationManager = (LocationManager) ctx
                .getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.addTestProvider(mProviderName, false, false, false,
                false, true, true, true, 0, 5);
        mLocationManager.setTestProviderEnabled(mProviderName, true);
    }

    public void pushLocation(double lat, double lon) {

        Location mockLocation = new Location(mProviderName);
        mockLocation.setLatitude(lat);
        mockLocation.setLongitude(lon);
        mockLocation.setAltitude(0);
        mockLocation.setTime(System.currentTimeMillis());
        mockLocation
                .setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        mockLocation.setAccuracy(mockAccuracy);

        mLocationManager.setTestProviderLocation(mProviderName, mockLocation);

    }

    public void shutdown() {
        mLocationManager.removeTestProvider(mProviderName);
    }
}
