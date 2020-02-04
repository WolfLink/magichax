package me.nathanp.magiccreatures.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @BindingAdapter("src")
    @JvmStatic
    fun setImageSrc(view: ImageView, resource: Int) {
        Glide.with(view.context).load(resource).into(view)
    }

    @BindingAdapter("srcCompat")
    @JvmStatic
    fun setImageSrcCompat(view: ImageView, resource: Int) {
        Glide.with(view.context).load(resource).into(view)
    }
}