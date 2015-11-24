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

/**
 * Created by morganwilde on 24/11/2015.
 */
public class LocationMaster {
    public static final String TAG = "LocationMaster";
    // Properties
    private GoogleApiClient mGoogleApiClient;
    private LocationListener mLocationListener;
    private LocationRequest mLocationRequest;

    public LocationMaster(Context context) {
        // Create the Google API client
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                    }
                    @Override
                    public void onConnectionSuspended(int i) {

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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
    }
    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocationListener);
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
