package co.developertime.android.escher;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class StoredLocation {
    private double mAccuracy;
    private double mAltitude;
    private double mLatitude;
    private double mLongitude;
    private long mTime;

    public StoredLocation(double accuracy, double altitude, double latitude, double longitude, long time) {
        mAccuracy = accuracy;
        mAltitude = altitude;
        mLatitude = latitude;
        mLongitude = longitude;
        mTime = time;
    }
}
