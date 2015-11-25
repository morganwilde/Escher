package co.developertime.android.escher;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class MainNavigationFragment extends Fragment {
    public static final String TAG = "MainNavigationFragment";
    public static int NAVIGATION_BUTTON_COLOR_ON = R.color.colorPrimary;
    public static int NAVIGATION_BUTTON_COLOR_OFF = R.color.colorLightForeground;
    public static int NAVIGATION_BUTTON_TEXT_COLOR_ON = R.color.colorLightForeground;
    public static int NAVIGATION_BUTTON_TEXT_COLOR_OFF = R.color.colorDarkForeground;
    public static String INITIAL_FRAGMENT_NAME = "Settings";

    // Interface
    public interface OnChangeListener {
        public void onFragmentSelection(String name);
    }
    public interface Delegate {
        public void navigateToFragmentWithName(String name);
    }

    private View.OnClickListener mOnNavigationButtonClickListener;

    private Map<String, Boolean> mNavigationButtonStates = new HashMap<>();
    private Map<String, Button> mNavigationButtonReferences = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_main, container, false);

        // Fetch references
        mNavigationButtonReferences.put("Settings", (Button) view.findViewById(R.id.navigation_button_settings));
        mNavigationButtonReferences.put("Map", (Button) view.findViewById(R.id.navigation_button_map));
        mNavigationButtonReferences.put("Analysis", (Button) view.findViewById(R.id.navigation_button_analysis));

        // Assign listeners
        for (String key : mNavigationButtonReferences.keySet()) {
            mNavigationButtonReferences.get(key).setOnClickListener(onNavigationButtonClickListener());
        }

        mNavigationButtonStates.put("Settings", "Settings" == INITIAL_FRAGMENT_NAME);
        mNavigationButtonStates.put("Map", "Map" == INITIAL_FRAGMENT_NAME);
        mNavigationButtonStates.put("Analysis", "Analysis" == INITIAL_FRAGMENT_NAME);

        updateNavigationButtons();

        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    // Navigation button listeners
    private View.OnClickListener onNavigationButtonClickListener() {
        if (mOnNavigationButtonClickListener == null) {
            mOnNavigationButtonClickListener = new View.OnClickListener() {
                public void onClick(View v) {onNavigationButtonClick((Button) v);}
            };
        }
        return mOnNavigationButtonClickListener;
    }
    private void onNavigationButtonClick(Button button) {
        for (String name : mNavigationButtonReferences.keySet()) {
            Button navigationButton = mNavigationButtonReferences.get(name);
            if (navigationButton == button) {
                toggleButtonToState(name, true);
                ((OnChangeListener) getActivity()).onFragmentSelection(name);
            }
        }
    }
    public void enableNavigationButtonWithName(String name) {
        onNavigationButtonClick(mNavigationButtonReferences.get(name));
    }

    // Navigation button toggles
    private void updateNavigationButtons() {
        int buttonTextColor;
        int[] buttonBackgroundTint;
        int[][] states = new int[][] {new int[] {}};
        for (String key : mNavigationButtonStates.keySet()) {
            if (mNavigationButtonStates.get(key)) {
                // Button is in ON state
                buttonTextColor = ContextCompat.getColor(getActivity(), NAVIGATION_BUTTON_TEXT_COLOR_ON);
                buttonBackgroundTint = new int[] {ContextCompat.getColor(getActivity(), NAVIGATION_BUTTON_COLOR_ON)};
            } else {
                // Button is in OFF state
                buttonTextColor = ContextCompat.getColor(getActivity(), NAVIGATION_BUTTON_TEXT_COLOR_OFF);
                buttonBackgroundTint = new int[] {ContextCompat.getColor(getActivity(), NAVIGATION_BUTTON_COLOR_OFF)};
            }
            Button button = mNavigationButtonReferences.get(key);
            button.setBackgroundTintList(new ColorStateList(states, buttonBackgroundTint));
            button.setTextColor(buttonTextColor);
        }
    }
    private void toggleButtonToState(String name, boolean state) {
        for (String key : mNavigationButtonStates.keySet()) {
            mNavigationButtonStates.put(key, !state);
        }
        mNavigationButtonStates.put(name, state);
        updateNavigationButtons();
    }
}
