package co.developertime.android.escher;

import android.graphics.Rect;
import android.util.Log;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class MapCell {
    public static final String TAG = "MapCell";

    private Rect mBounds;
    private String mName;

    public Rect getBounds() {
        return mBounds;
    }
    public String getName() {
        return mName;
    }


    public MapCell(double longitudeFrom, double longituteTo, double latitudeFrom, double latitudeTo) {

    }
    public MapCell(Rect bounds, String name) {
        mBounds = bounds;
        mName = name;
    }
}
