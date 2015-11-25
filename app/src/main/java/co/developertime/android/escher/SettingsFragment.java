package co.developertime.android.escher;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class SettingsFragment extends Fragment implements LocationMaster.OnAccuracyChangeListener {
    public static final String TAG = "SettingsFragment";
    public static String LOCATION_TRACKING_TOGGLE_BUTTON_TEXT_ON = "Stop";
    public static String LOCATION_TRACKING_TOGGLE_BUTTON_TEXT_OFF = "Start";
    public static int LOCATION_TRACKING_TOGGLE_BUTTON_COLOR_ON = R.color.colorNegative;
    public static int LOCATION_TRACKING_TOGGLE_BUTTON_COLOR_OFF = R.color.colorPositive;

    // Properties
    private LocationMaster mLocationMaster;
    private static boolean mInitialLaunch = true;
    // Views
    private Button mLocationTrackingToggleButton;
    private TextView mWaitingForAccuracyTextView;
    private TextView mAccuracyTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationMaster = LocationMaster.getLocationMaster(getActivity());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mLocationTrackingToggleButton = (Button) view.findViewById(R.id.location_tracking_toggle_button);
        mWaitingForAccuracyTextView = (TextView) view.findViewById(R.id.waiting_for_accuracy_text_view);
        mAccuracyTextView = (TextView) view.findViewById(R.id.accuracy_text_view);

        mLocationTrackingToggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mLocationMaster.willBecomeActive()) {
                    mLocationMaster.startLocationUpdates();
                    updateWaitingForAccuracyTextView(true);
                } else {
                    mLocationMaster.stopLocationUpdates();
                    updateWaitingForAccuracyTextView(false);
                    updateAccuracyTextView(-1);
                }
                updateTrackingToggleButton();
            }
        });

        updateWaitingForAccuracyTextView(false);
        updateAccuracyTextView(-1);
        updateTrackingToggleButton();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLocationMaster.addListenerForAccuracyChanges(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        mLocationMaster.removeListenerForAccuracyChanges(this);
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
    public void updateWaitingForAccuracyTextView(boolean visible) {
        if (visible) {
            mWaitingForAccuracyTextView.setText(R.string.settings_fragment_waiting_for_accuracy_on);
        } else {
            mWaitingForAccuracyTextView.setText(R.string.settings_fragment_waiting_for_accuracy_off);
        }
    }
    public void updateAccuracyTextView(int accuracy) {
        if (accuracy > 0) {
            mAccuracyTextView.setText(String.format(getResources().getString(R.string.settings_fragment_accuracy), accuracy));
        } else if (accuracy == -1) {
            mAccuracyTextView.setText("");
        }
    }

    // Listeners
    public void onAccuracyChanged(float accuracy) {
        updateAccuracyTextView(Math.round(accuracy));
    }
    public void onAccuracyChangedToGood() {
        updateWaitingForAccuracyTextView(false);
        if (SettingsFragment.mInitialLaunch) {
            ((MainNavigationFragment.Delegate) getActivity()).navigateToFragmentWithName("Map");
            SettingsFragment.mInitialLaunch = false;
        }
    }
}
