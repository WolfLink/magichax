package me.nathanp.magiccreatures.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import me.nathanp.magiccreatures.model.Creature

class CreatureView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var creature: Creature? = null
        set(value) {field = value.also { updateIcon() }}
    var drawable: Drawable? = null

    fun updateIcon() {
        if (creature != null) {
            drawable = context.resources.getDrawable(creature!!.drawableId, null)
            postInvalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.v(LOG_TAG, "onMeasure w " + MeasureSpec.toString(widthMeasureSpec))
        Log.v(LOG_TAG, "onMeasure h " + MeasureSpec.toString(heightMeasureSpec))
        setMeasuredDimension(400, 400)
    }

    override fun onDraw(canvas: Canvas) {
        drawable?.run {
            canvas.getClipBounds(this.bounds)
            this.draw(canvas)
        }
    }

    companion object {
        const val LOG_TAG = "CreatureView"
    }
}