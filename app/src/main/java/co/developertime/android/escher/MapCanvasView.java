package co.developertime.android.escher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

/**
 * Created by morganwilde on 23/11/2015.
 */
public class MapCanvasView extends View {
    // Statics
    public static final String TAG = "MapCanvasView";
    private static final int BACKGROUND_ROUNDED_RECTANGLE_CORNER_RADIUS = 10;
    private static final int BACKGROUND_ROUNDED_RECTANGLE_BORDER_WIDTH = 5;

    // Properties
    private float mScreenDensity;
    private Rect mCanvasBounds;
    private MapCellGrid mMapCellGrid;

    // Drawables
    private DrawableShapeStack mDrawableShapeStack = new DrawableShapeStack();
    private ShapeDrawable mBackgroundShape;

    public MapCanvasView(Context context) {
        super(context);
        mScreenDensity = context.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMapCellGrid.draw(canvas, getContext());
        mBackgroundShape.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCanvasBounds = new Rect(0, 0, w, h);
        mMapCellGrid = new MapCellGrid(0, 0, w, h);
        createBackgroundShape();
    }

    // Drawing
    private void createBackgroundShape() {
        // Size
        int x = 0;
        int y = 0;
        int width = mCanvasBounds.width();
        int height = mCanvasBounds.height();
        // Shape
        if (mBackgroundShape == null) {
            float radius = BACKGROUND_ROUNDED_RECTANGLE_CORNER_RADIUS;
            float topLeftRadius = radius * mScreenDensity;
            float topRightRadius = radius * mScreenDensity;
            float bottomLeftRadius = radius * mScreenDensity;
            float bottomRightRadius = radius * mScreenDensity;
            // Inner Corners
            float[] innerRadii = {
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius
            };
            // Inset rectangle
            RectF insetRectangle = new RectF(
                    BACKGROUND_ROUNDED_RECTANGLE_BORDER_WIDTH * mScreenDensity,
                    BACKGROUND_ROUNDED_RECTANGLE_BORDER_WIDTH * mScreenDensity,
                    BACKGROUND_ROUNDED_RECTANGLE_BORDER_WIDTH * mScreenDensity,
                    BACKGROUND_ROUNDED_RECTANGLE_BORDER_WIDTH * mScreenDensity
            );
            // Outer Corners
            float[] outerRadii = {
                    0, 0,
                    0, 0,
                    0, 0,
                    0, 0
            };

            mBackgroundShape = new ShapeDrawable(new RoundRectShape(outerRadii, insetRectangle, innerRadii));
            mBackgroundShape.getPaint().setColor(ContextCompat.getColor(getContext(), R.color.colorLightForeground));
        }
        mBackgroundShape.setBounds(x, y, x + width, y + height);
    }
    public void addShapeAt(int radius) {
        ShapeDrawable shape = new ShapeDrawable(new OvalShape());
        shape.getPaint().setColor(0xffc0392b);
        shape.setBounds(
                mBackgroundShape.getBounds().centerX() - radius / 2,
                mBackgroundShape.getBounds().centerY() - radius / 2,
                radius,
                radius
        );
        mDrawableShapeStack.appendShape(shape);
    }

    public int getGridOriginX() {return mMapCellGrid.getOriginX();}
    public int getGridOriginY() {return mMapCellGrid.getOriginY();}
    public void updateGridOrigin(int originX, int originY) {
        mMapCellGrid.updateOrigin(originX, originY);
        invalidate();
    }
    public void saveGridOrigin(int originX, int originY) {
        mMapCellGrid.saveOrigin(originX, originY);
        invalidate();
    }
}
