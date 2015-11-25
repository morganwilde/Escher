package co.developertime.android.escher;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements MainNavigationFragment.OnChangeListener, MainNavigationFragment.Delegate {
    // Statics
    public static final String TAG = "MainActivity";
    private static final int TOOLBAR_PADDING_LEFT = 10;

    // Fragments
    private MainNavigationFragment mMainNavigationFragment;

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
        mMainNavigationFragment = (MainNavigationFragment) fragmentManager.findFragmentById(R.id.fragment_navigation_container);

        if (fragment == null) {
            onFragmentSelection(mMainNavigationFragment.INITIAL_FRAGMENT_NAME);
        }
        if (mMainNavigationFragment == null) {
            mMainNavigationFragment = new MainNavigationFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_navigation_container, mMainNavigationFragment);
            transaction.commit();
        }

        // Create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Escher");
        toolbar.setContentInsetsAbsolute((int) (TOOLBAR_PADDING_LEFT * mScreenDensity), 0);
        setActionBar(toolbar); // This creates a copy and gets rid of the original Toolbar instance
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

    // Navigation listener
    public void onFragmentSelection(String name) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, createFragmentWithName(name));
        transaction.commit();
    }
    public Fragment createFragmentWithName(String name) {
        switch (name) {
            case "Settings": return new SettingsFragment();
            case "Map": return new MapFragment();
            case "Analysis": return new AnalysisFragment();
            default: return null;
        }
    }

    // Navigation delegate
    public void navigateToFragmentWithName(String name) {
        mMainNavigationFragment.enableNavigationButtonWithName(name);
        Log.i(TAG, name);
    }
}
