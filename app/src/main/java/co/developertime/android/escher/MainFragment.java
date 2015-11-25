package co.developertime.android.escher;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";
    public static String LOCATION_TRACKING_TOGGLE_BUTTON_TEXT_ON = "Stop";
    public static String LOCATION_TRACKING_TOGGLE_BUTTON_TEXT_OFF = "Start";
    public static int LOCATION_TRACKING_TOGGLE_BUTTON_COLOR_ON = R.color.colorNegative;
    public static int LOCATION_TRACKING_TOGGLE_BUTTON_COLOR_OFF = R.color.colorPositive;

    // Properties
    private LocationMaster mLocationMaster;
    private Button mLocationTrackingToggleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationMaster = LocationMaster.getLocationMaster(getActivity());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mLocationTrackingToggleButton = (Button) view.findViewById(R.id.location_tracking_toggle_button);
        mLocationTrackingToggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mLocationMaster.willBecomeActive()) {
                    mLocationMaster.startLocationUpdates();
                } else {
                    mLocationMaster.stopLocationUpdates();
                }
                updateTrackingToggleButton();
            }
        });
        updateTrackingToggleButton();

        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    // Fragment setup
    public void updateTrackingToggleButton() {
        if (mLocationTrackingToggleButton != null) {
            String buttonText;
            int[] buttonBackgroundTint;
            int[][] states = new int[][] {
                    new int[] {}
            };
            if (mLocationMaster.willBecomeActive()) {
                buttonText = LOCATION_TRACKING_TOGGLE_BUTTON_TEXT_ON;
                buttonBackgroundTint = new int[] {ContextCompat.getColor(getActivity(), LOCATION_TRACKING_TOGGLE_BUTTON_COLOR_ON)};
            } else {
                buttonText = LOCATION_TRACKING_TOGGLE_BUTTON_TEXT_OFF;
                buttonBackgroundTint = new int[] {ContextCompat.getColor(getActivity(), LOCATION_TRACKING_TOGGLE_BUTTON_COLOR_OFF)};
            }
            mLocationTrackingToggleButton.setText(buttonText);
            mLocationTrackingToggleButton.setBackgroundTintList(new ColorStateList(states, buttonBackgroundTint));
        }
    }
}
