package co.developertime.android.escher;

import android.app.Fragment;
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
public class MapFragment extends Fragment implements View.OnTouchListener {
    public static final String TAG = "MapFragment";

    // Properties
    private LinearLayout mMapCanvasContainerView;
    private MapCanvasView mMapCanvasView;
    private float deltaX;
    private float deltaY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapCanvasContainerView = (LinearLayout) view.findViewById(R.id.map_canvas_container_view);
        mMapCanvasView = new MapCanvasView(getActivity());
        mMapCanvasContainerView.addView(mMapCanvasView);

        mMapCanvasContainerView.setOnTouchListener(this);

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
                deltaX = v.getX() - event.getRawX() + mMapCanvasView.getGridOriginX();
                deltaY = v.getY() - event.getRawY() + mMapCanvasView.getGridOriginY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMapCanvasView.updateGridOrigin(
                        Math.round(event.getRawX() + deltaX),
                        Math.round(event.getRawY() + deltaY)
                );
                break;
            case MotionEvent.ACTION_UP:
                mMapCanvasView.saveGridOrigin(
                        Math.round(event.getRawX() + deltaX),
                        Math.round(event.getRawY() + deltaY)
                );
            default:
                return false;
        }
        return true;
    }
}
