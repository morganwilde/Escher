package co.developertime.android.escher;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
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

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        Fragment fragmentNavigationMain = fragmentManager.findFragmentById(R.id.fragment_navigation_container);

        if (fragment == null) {
            fragment = new MainFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        if (fragmentNavigationMain == null) {
            fragmentNavigationMain = new MainNavigationFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_navigation_container, fragmentNavigationMain);
            transaction.commit();
        }

        // Create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Escher");
        toolbar.setContentInsetsAbsolute((int) (TOOLBAR_PADDING_LEFT * mScreenDensity), 0);
        setActionBar(toolbar); // This creates a copy and gets rid of the original Toolbar instance
//
//        // Create the map's canvas
//        mMapCanvasViewContainer = (RelativeLayout) findViewById(R.id.map_canvas_container);
//        mMapCanvasView = new MapCanvasView(this);
//        mMapCanvasViewContainer.addView(mMapCanvasView);
//
//        // Google Play services
//        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        Log.i(TAG, "errorCode: " + errorCode);
//
//        mLocationMaster = new LocationMaster(this);
    }

    @Override
    protected void onStart() {super.onStart();}
    @Override
    protected void onResume() {super.onResume();}
    @Override
    protected void onPause() {super.onPause();}
    @Override
    protected void onStop() {super.onStop();}
    @Override
    protected void onDestroy() {super.onDestroy();}
}
