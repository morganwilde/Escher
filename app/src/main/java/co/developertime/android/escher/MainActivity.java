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
    private LocationMaster mLocationMaster;

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

        mLocationMaster = new LocationMaster(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationMaster.startLocationUpdates();
    }
    @Override
    protected void onResume() {super.onResume();}
    @Override
    protected void onPause() {super.onPause();}
    @Override
    protected void onStop() {
        super.onStop();
        mLocationMaster.stopLocationUpdates();
    }
    @Override
    protected void onDestroy() {super.onDestroy();}
}
