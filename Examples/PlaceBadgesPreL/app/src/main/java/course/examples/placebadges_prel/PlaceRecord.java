package course.examples.placebadges_prel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;

import java.text.DateFormat;
import java.util.Date;

public class PlaceRecord {

    private String mFlagUrl;
    private String mCountryName;
    private String mPlaceName;
    private Bitmap mFlagBitmap;
    private Location mLocation;
    private String mDate;

    private final static String COUNTRY_NAME_EXTRA = "countryName";
    private final static String PLACE_NAME_EXTRA = "placeName";
    private final static String DATE_STRING_EXTRA = "dateString";
    private final static String FLAG_BITMAP_EXTRA = "flagBitmap";
    private final static String LNG_EXTRA = "longitude";
    private final static String LAT_EXTRA = "latitude";

    public PlaceRecord(String flagUrl, String country, String place) {
        mFlagUrl = flagUrl;
        mCountryName = country;
        mPlaceName = place;
        mDate = DateFormat.getDateTimeInstance().format(new Date());
    }

    public PlaceRecord(Location location) {
        mLocation = location;
        mDate = DateFormat.getDateTimeInstance().format(new Date());
    }

    public PlaceRecord(Intent intent) {
        setCountryName(intent.getStringExtra(COUNTRY_NAME_EXTRA));
        setFlagBitmap((Bitmap) intent.getParcelableExtra(FLAG_BITMAP_EXTRA));
        setPlace(intent.getStringExtra(PLACE_NAME_EXTRA));
        setDate(intent.getStringExtra(DATE_STRING_EXTRA));
        Location tmpLocation = new Location(LocationManager.NETWORK_PROVIDER);
        tmpLocation.setLatitude(intent.getDoubleExtra(LAT_EXTRA, 0.0));
        tmpLocation.setLongitude(intent.getDoubleExtra(LNG_EXTRA, 0.0));
        setLocation(tmpLocation);
    }

    public String getFlagUrl() {
        return mFlagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        mFlagUrl = flagUrl;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public void setCountryName(String country) {
        mCountryName = country;
    }

    public String getPlace() {
        return mPlaceName;
    }

    public void setPlace(String place) {
        this.mPlaceName = place;
    }

    public Bitmap getFlagBitmap() {
        return mFlagBitmap;
    }

    public void setFlagBitmap(Bitmap flagBitmap) {
        mFlagBitmap = flagBitmap;
    }

    public String getDate() {
        return mDate;
    }

    private void setDate(String date) {
        mDate = date;
    }

    public boolean intersects(Location location) {

        double tolerance = 1000;
        return (mLocation.distanceTo(location) <= tolerance);

    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public Location getLocation() {
        return mLocation;
    }

    @Override
    public String toString() {
        return "Place: " + mPlaceName + " Country: " + mCountryName;
    }

    public Intent packageIntent() {

        Intent data = new Intent();
        data.putExtra(COUNTRY_NAME_EXTRA, mCountryName);
        data.putExtra(PLACE_NAME_EXTRA, mPlaceName);
        data.putExtra(DATE_STRING_EXTRA, mDate);
        data.putExtra(LAT_EXTRA, mLocation.getLatitude());
        data.putExtra(LNG_EXTRA, mLocation.getLongitude());
        data.putExtra(FLAG_BITMAP_EXTRA, mFlagBitmap);

        return data;
    }
}
