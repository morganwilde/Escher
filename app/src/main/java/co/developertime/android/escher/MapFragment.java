package co.developertime.android.escher;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class MapFragment extends Fragment {
    public static final String TAG = "MapFragment";

    // Properties
    private LinearLayout mMapCanvasContainerView;
    private MapCanvasView mMapCanvasView;

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

        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
    }
}
