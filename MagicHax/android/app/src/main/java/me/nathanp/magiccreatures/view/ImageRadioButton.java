package me.nathanp.magiccreatures.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

public class ImageRadioButton extends AppCompatRadioButton {

    public ImageRadioButton(Context context) {
        super(context);
    }

    public ImageRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = MeasureSpec.getSize(widthMeasureSpec);
        Drawable drawable = getBackground();

        float aspectRatio = (float)drawable.getIntrinsicHeight() / (float)drawable.getIntrinsicWidth();
        setMeasuredDimension(desiredWidth, (int)(desiredWidth * aspectRatio));
    }
}
