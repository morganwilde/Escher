package co.developertime.android.escher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.Log;
import android.view.View;

/**
 * Created by morganwilde on 23/11/2015.
 */
public class MapCanvasView extends View {
    // Statics
    public static final String TAG = "MapCanvasView";
    private static final int BACKGROUND_ROUNDED_RECTANGLE_CORNER_RADIUS = 10;

    // Properties
    private float mScreenDensity;
    private class Padding {
        float mScreenDensity;
        int mTop;
        int mRight;
        int mBottom;
        int mLeft;
        public Padding(float screenDensity, int top, int right, int bottom, int left) {
            this.mScreenDensity = screenDensity;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
            this.mLeft = left;
        }
        public int top() {return mTop * (int) mScreenDensity;}
        public int right() {return mRight * (int) mScreenDensity;}
        public int bottom() {return mBottom * (int) mScreenDensity;}
        public int left() {return mLeft * (int) mScreenDensity;}
        public int horizontal() {return left() + right();}
        public int vertical() {return top() + bottom();}
    }
    private Padding mPadding;
    private Rect mCanvasBounds;

    // Drawables
    private DrawableShapeStack mDrawableShapeStack = new DrawableShapeStack();
    private ShapeDrawable mBackgroundShape;

    public MapCanvasView(Context context) {
        super(context);
        mScreenDensity = context.getResources().getDisplayMetrics().density;
        mPadding = new Padding(mScreenDensity, 10, 10, 10, 10);
        setBackgroundColor(0xffecf0f1); // Clouds
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
        mBackgroundShape.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCanvasBounds = new Rect(0, 0, w, h);
        Log.i(TAG, "" + mCanvasBounds);
        createBackgroundShape();
    }

    // Drawing
    private void createBackgroundShape() {
        // Size
        int x = mPadding.left();
        int y = mPadding.top();
        int width = mCanvasBounds.width() - mPadding.horizontal();
        int height = mCanvasBounds.height() - mPadding.vertical();
        // Shape
        if (mBackgroundShape == null) {
            // Corners
            float radius = BACKGROUND_ROUNDED_RECTANGLE_CORNER_RADIUS;
            float topLeftRadius = radius * mScreenDensity;
            float topRightRadius = radius * mScreenDensity;
            float bottomLeftRadius = radius * mScreenDensity;
            float bottomRightRadius = radius * mScreenDensity;
            float[] outerRadii = {
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius
            };

            mBackgroundShape = new ShapeDrawable(new RoundRectShape(outerRadii, null, null));
            mBackgroundShape.getPaint().setColor(0xff95a5a6); // Concrete
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
}
