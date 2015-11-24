package co.developertime.android.escher;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by morganwilde on 23/11/2015.
 */
public class DrawableShapeStack {
    private List<ShapeDrawable> mStack;
    public DrawableShapeStack() {
        mStack = new ArrayList<>();
    }
    public void appendShape(ShapeDrawable shape) {
        mStack.add(shape);
    }
    public void drawOnCanvas(Canvas canvas) {
        for (ShapeDrawable shape: mStack) {
            shape.draw(canvas);
        }
    }
    public boolean contains(ShapeDrawable shape) {
        return mStack.contains(shape);
    }
}
