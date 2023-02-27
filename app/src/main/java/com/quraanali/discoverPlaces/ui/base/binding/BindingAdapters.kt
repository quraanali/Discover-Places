package com.quraanali.discoverPlaces.ui.base.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView


interface BindingAdapters {

    @BindingAdapter(
        "appImageUrl",
        "appCorners",
        "appStrokeWidth",
        "appStrokeColor",
        "appIsCircle",
        "appAddTopCorners",
        "appError",
        "appPlaceholder",
        requireAll = false
    )
    fun <T> AppCompatImageView.loadImage(
        model: T,
        corners: Float = 0f,
        strokeWidth: Float = 0f,
        strokeColor: Int,
        isCircle: Boolean,
        addTopCorners: Float,
        error: Drawable?,
        placeholder: Drawable?
    )

    @BindingAdapter(
        "appImageUrlWithBlur"
    )
    fun <T> AppCompatImageView.loadImageWithBlurEffect(
        model: T,
    )

    @BindingAdapter("appSrc")
    fun setDrawable(appCompatImageView: AppCompatImageView, res: Int)


    @BindingAdapter("appImageViewTint")
    fun setImageViewTint(
        imageView: ImageView,
        tint: Int,
    )

    @BindingAdapter("appTxtRes")
    fun setTextViewResString(
        txtView: MaterialTextView,
        res: Int?,
    )
}