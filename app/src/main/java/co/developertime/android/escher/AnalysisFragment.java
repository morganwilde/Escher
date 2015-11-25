package co.developertime.android.escher;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class AnalysisFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorLightForeground));
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
    }
}
