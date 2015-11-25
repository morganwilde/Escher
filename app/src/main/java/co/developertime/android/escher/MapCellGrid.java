package co.developertime.android.escher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class MapCellGrid {
    public static final String TAG = "MapCellGrid";
    public static final int CELL_COUNT = 9; // Must be a square number

    private List<MapCell> mMapCells;
    private int mOriginX;
    private int mOriginY;
    private int mWidthPixels;
    private int mHeightPixels;

    public MapCellGrid(int originX, int originY, int widthPixels, int heightPixels) {
        mOriginX = originX;
        mOriginY = originY;
        mWidthPixels = widthPixels;
        mHeightPixels = heightPixels;

        int gridSideLengthInCells = (int) Math.round(Math.sqrt(CELL_COUNT));
        int cellWidthPixels = mWidthPixels / gridSideLengthInCells;
        int cellHeightPixels = mHeightPixels / gridSideLengthInCells;

        mMapCells = new ArrayList<>();
        for (int v = 0; v < gridSideLengthInCells; v++) {
            for (int h = 0; h < gridSideLengthInCells; h++) {
                mMapCells.add(new MapCell(
                        new Rect(
                                h * cellWidthPixels,
                                v * cellHeightPixels,
                                h * cellWidthPixels + cellWidthPixels,
                                v * cellHeightPixels + cellHeightPixels),
                        "" + (v * gridSideLengthInCells + h)
                ));
            }
        }
    }

    public void draw(Canvas canvas, Context context) {
        for (MapCell cell : mMapCells) {
            ShapeDrawable cellShape = new ShapeDrawable(new RectShape());
            Rect cellBounds = cell.getBounds();
            cellShape.setBounds(
                    cellBounds.left + mOriginX,
                    cellBounds.top + mOriginY,
                    cellBounds.right + mOriginX,
                    cellBounds.bottom + mOriginY
            );
            cellShape.getPaint().setColor(ContextCompat.getColor(context, R.color.colorDarkerForeground));
            cellShape.draw(canvas);

            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
            textPaint.setTextSize((float) (14 * 3.5));
            canvas.drawText(
                    cell.getName(),
                    cellBounds.centerX() + mOriginX,
                    cellBounds.centerY() + mOriginY,
                    textPaint
            );
        }
    }

    public int getOriginX() {return mOriginX;}
    public int getOriginY() {return mOriginY;}
    public void updateOrigin(int originX, int originY) {
        mOriginX = originX;
        mOriginY = originY;
    }
    public void saveOrigin(int originX, int originY) {
        // TODO: this is where I need to create a new grid to accommodate origin changes
        mOriginX = originX;
        mOriginY = originY;
    }
}
