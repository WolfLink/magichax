package me.nathanp.magiccreatures.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import me.nathanp.magiccreatures.model.Creature;

public class CreatureView extends View {
    public static final String LOG_TAG = "CreatureView";
    Creature creature;
    Drawable drawable;
    Rect clipBounds = new Rect();

    public CreatureView(Context context) {
        super(context);
        updateIcon();
    }

    public CreatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        updateIcon();
    }

    public CreatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        updateIcon();
    }

    public CreatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        updateIcon();
    }

    void setCreature(Creature creature) {
        this.creature = creature;
        updateIcon();
    }

    private void updateIcon() {
        if (creature != null) {
            drawable = getContext().getResources().getDrawable(creature.getDrawableId(), null);
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.v(LOG_TAG, "onMeasure w " + MeasureSpec.toString(widthMeasureSpec));
        Log.v(LOG_TAG, "onMeasure h " + MeasureSpec.toString(heightMeasureSpec));
        setMeasuredDimension(400, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (drawable != null) {
            canvas.getClipBounds(clipBounds);  // Adjust this for where you want it
            drawable.setBounds(clipBounds);
            drawable.draw(canvas);
        }
    }
}
