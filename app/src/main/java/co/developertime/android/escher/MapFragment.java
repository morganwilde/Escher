package co.developertime.android.escher;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class MapFragment extends Fragment implements View.OnTouchListener, LocationMaster.OnAccuracyChangeListener {
    public static final String TAG = "MapFragment";
    public static int LATITUDE_MINUTE_METERS = 1853; // 1 minute of latitude in meters
    public static int LONGITUDE_MINUTE_METERS = 1853; // 1 minute of latitude in meters
    public static int MAP_RENDER_SCALE_INCREMENT = 10; // Number of times to

    // Properties
    private LinearLayout mMapCanvasContainerView;
    private MapCanvasView mMapCanvasView;
    private float mDeltaX;
    private float mDeltaY;
    private double mMapRenderScale = 10 * MAP_RENDER_SCALE_INCREMENT; // Number of meters equating the pixel width of the container view
    private boolean hasInitialLocation = false;

    private StoredLocationMaster mStoredLocationMaster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStoredLocationMaster = StoredLocationMaster.getInstance(getActivity());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapCanvasContainerView = (LinearLayout) view.findViewById(R.id.map_canvas_container_view);
        mMapCanvasView = new MapCanvasView(getActivity());
        mMapCanvasContainerView.addView(mMapCanvasView);

        mMapCanvasContainerView.setOnTouchListener(this);

        LocationMaster.getLocationMaster(getActivity()).addListenerForAccuracyChanges(this);
        LocationMaster.getLocationMaster(getActivity()).startLocationUpdates();

        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    // OnTouchListener
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDeltaX = v.getX() - event.getRawX() + mMapCanvasView.getGridOriginX();
                mDeltaY = v.getY() - event.getRawY() + mMapCanvasView.getGridOriginY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMapCanvasView.updateGridOrigin(
                        Math.round(event.getRawX() + mDeltaX),
                        Math.round(event.getRawY() + mDeltaY)
                );
                break;
            case MotionEvent.ACTION_UP:
                mMapCanvasView.saveGridOrigin(
                        Math.round(event.getRawX() + mDeltaX),
                        Math.round(event.getRawY() + mDeltaY)
                );
            default:
                return false;
        }
        return true;
    }

    // OnAccuracyChangeListener
    public void onAccuracyChanged(float accuracy) {
        LocationMaster locationMaster = LocationMaster.getLocationMaster(getActivity());
        if (locationMaster.hasStartedAccurateLocationUpdates() && !hasInitialLocation) {
            hasInitialLocation = true;
            Location currentLocation = locationMaster.getLastRecordedLocation();
            Log.i(TAG, "longitude: " + currentLocation.getLongitude());
            Log.i(TAG, "latitude: " + currentLocation.getLatitude());

            double widthHeightRatio =  mMapCanvasView.getCanvasBounds().height() / mMapCanvasView.getCanvasBounds().width();
            double leftLongitude = currentLocation.getLongitude() - mMapRenderScale / 1000000;
            double rightLongitude = currentLocation.getLongitude() + mMapRenderScale / 1000000;
            double topLatitude = currentLocation.getLatitude() - (mMapRenderScale / 1000000 ) * widthHeightRatio;
            double bottomLatitude = currentLocation.getLatitude() + (mMapRenderScale / 1000000 ) * widthHeightRatio;

            mMapCanvasView.updateGridLongitudeAndLatitude(
                    leftLongitude,
                    rightLongitude,
                    topLatitude,
                    bottomLatitude
            );
            Log.i(TAG, "leftLongitude: " + leftLongitude);
            Log.i(TAG, "rightLongitude: " + rightLongitude);
            Log.i(TAG, "topLatitude: " + topLatitude);
            Log.i(TAG, "bottomLatitude: " + bottomLatitude);
        }
    }
    public void onAccuracyChangedToGood() {}
}
