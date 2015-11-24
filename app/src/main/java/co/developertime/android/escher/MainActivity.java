package co.developertime.android.escher;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {
    // Statics
    public static final String TAG = "MainActivity";
    private static final int TOOLBAR_PADDING_LEFT = 10;

    // Views
    private RelativeLayout mMapCanvasViewContainer;
    private MapCanvasView mMapCanvasView;

    // Properties
    private float mScreenDensity;
    private GoogleApiClient mGoogleApiClient;
    private LocationListener mLocationListener;

    // Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScreenDensity = getResources().getDisplayMetrics().density;

        // Create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Escher");
        toolbar.setSubtitle("Map");
        toolbar.setContentInsetsAbsolute((int) (TOOLBAR_PADDING_LEFT * mScreenDensity), 0);
        setSupportActionBar(toolbar); // This creates a copy and gets rid of the original Toolbar instance

        // Create the map's canvas
        mMapCanvasViewContainer = (RelativeLayout) findViewById(R.id.map_canvas_container);
        mMapCanvasView = new MapCanvasView(this);
        mMapCanvasViewContainer.addView(mMapCanvasView);

        // Google Play services
        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        Log.i(TAG, "errorCode: " + errorCode);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Log.i(TAG, "onConnected");
                        getLocation();
                    }
                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.i(TAG, "onConnectionSuspended " + i);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.i(TAG, "onConnectionFailed " + connectionResult);
                    }
                })
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onResume() {super.onResume();}
    @Override
    protected void onPause() {super.onPause();}
    @Override
    protected void onStop() {
        super.onStop();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocationListener);
        mGoogleApiClient.disconnect();
    }
    @Override
    protected void onDestroy() {super.onDestroy();}

    private void getLocation() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //request.setNumUpdates(1);
        request.setInterval(0);

        if (false) {
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
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
            };

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, mLocationListener);
        }
    }
}
