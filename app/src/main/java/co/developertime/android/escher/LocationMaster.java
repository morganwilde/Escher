package co.developertime.android.escher;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class LocationMaster {
    public static final String TAG = "LocationMaster";

    public interface OnAccuracyChangeListener {
        public void onAccuracyChanged(float accuracy);
        public void onAccuracyChangedToGood();
    }

    private List<OnAccuracyChangeListener> mOnAccuracyChangeListeners = new ArrayList<>();
    public void addListenerForAccuracyChanges(OnAccuracyChangeListener listener) {
        mOnAccuracyChangeListeners.add(listener);
    }
    public void removeListenerForAccuracyChanges(OnAccuracyChangeListener listener) {
        mOnAccuracyChangeListeners.remove(listener);
    }

    // Properties
    private boolean mShouldStartLocationUpdates = false;
    private boolean mHasStartedLocationUpdates = false;
    public boolean willBecomeActive() {return mShouldStartLocationUpdates;}
    public boolean hasBecomeActive() {return mHasStartedLocationUpdates;}

    private GoogleApiClient mGoogleApiClient;
    private LocationListener mLocationListener;
    private LocationRequest mLocationRequest;

    private static LocationMaster mInstance;
    public static LocationMaster getLocationMaster(Context context) {
        if (mInstance == null) {
            mInstance = new LocationMaster(context);
        }
        return mInstance;
    }

    private Location mLastRecordedLocation;

    private LocationMaster(Context context) {
        // Create the Google API client
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        if (mShouldStartLocationUpdates) {
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
                        }
                    }
                    @Override
                    public void onConnectionSuspended(int i) {
                        if (!mShouldStartLocationUpdates) {
                            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocationListener);
                        }
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                })
                .build();
        // Create the location listener
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mHasStartedLocationUpdates = true;
                mLastRecordedLocation = location;
                for (OnAccuracyChangeListener accuracyChangeListener : mOnAccuracyChangeListeners) {
                    accuracyChangeListener.onAccuracyChanged(location.getAccuracy());
                    if (location.getAccuracy() <= 10) {
                        accuracyChangeListener.onAccuracyChangedToGood();
                    }
                }
                logLocation(location);
            }
        };
        // Create the location request
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
    }

    // Methods
    public void startLocationUpdates() {
        mShouldStartLocationUpdates = true;
        mGoogleApiClient.connect();

    }
    public void stopLocationUpdates() {
        mHasStartedLocationUpdates = false;
        mShouldStartLocationUpdates = false;
        mGoogleApiClient.disconnect();
    }

    public void logLocation(Location location) {
        Log.i(TAG, "location recorded: " + location);
        Log.i(TAG, "location.getAccuracy() -> " + location.getAccuracy());
        Log.i(TAG, "location.getAltitude() -> " + location.getAltitude());
        Log.i(TAG, "location.getBearing() -> " + location.getBearing());
        Log.i(TAG, "location.getElapsedRealtimeNanos() -> " + location.getElapsedRealtimeNanos());
        Log.i(TAG, "location.getExtras() -> " + location.getExtras());
        Log.i(TAG, "location.getLatitude() -> " + location.getLatitude());
        Log.i(TAG, "location.getLongitude() -> " + location.getLongitude());
        Log.i(TAG, "location.getProvider() -> " + location.getProvider());
        Log.i(TAG, "location.getSpeed() -> " + location.getSpeed());
        Log.i(TAG, "location.getTime() -> " + location.getTime());
        Log.i(TAG, "location.hasAccuracy() -> " + location.hasAccuracy());
        Log.i(TAG, "location.hasAltitude() -> " + location.hasAltitude());
        Log.i(TAG, "location.hasBearing() -> " + location.hasBearing());
        Log.i(TAG, "location.hasSpeed() -> " + location.hasSpeed());
        Log.i(TAG, "location.isFromMockProvider() -> " + location.isFromMockProvider());
    }
}
