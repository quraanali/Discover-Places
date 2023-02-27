package com.quraanali.discoverPlaces.ui.base.binding

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textview.MaterialTextView
import com.quraanali.discoverPlaces.R
import com.quraanali.discoverPlaces.utils.RoundedCornersWithBorder
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import javax.inject.Inject

class BindingAdaptersImpl @Inject constructor() : BindingAdapters {

    override fun <T> AppCompatImageView.loadImage(
        model: T,
        corners: Float,
        strokeWidth: Float,
        strokeColor: Int,
        isCircle: Boolean,
        addTopCorners: Float,
        error: Drawable?,
        placeholder: Drawable?
    ) {
        model?.let {
            glideLoadImage(
                this@loadImage,
                it,
                corners,
                strokeWidth,
                strokeColor,
                isCircle,
                addTopCorners,
                error,
                placeholder
            )
        } ?: kotlin.run {
            Glide.with(context).clear(this)
            setImageResource(R.drawable.ic_launcher_background)
        }
    }

    private fun <T> glideLoadImage(
        img: AppCompatImageView,
        model: T,
        corners: Float,
        strokeWidth: Float,
        strokeColor: Int,
        isCircle: Boolean,
        addTopCorners: Float,
        error: Drawable?,
        placeholder: Drawable?
    ) {

        Glide.with(img.context)
            .load(model)
            .error(error)
            .placeholder(placeholder)
            .transform(
                let {
                    if (isCircle && strokeWidth > 0) {
                        CropCircleWithBorderTransformation(strokeWidth.toInt(), strokeColor)
                    } else if (isCircle) {
                        CircleCrop()
                    } else if (corners > 0) {
                        MultiTransformation(
                            CenterCrop(),
                            RoundedCornersWithBorder(
                                corners.toInt(),
                                strokeWidth.toInt(),
                                strokeColor
                            )
                        )
                    } else if (addTopCorners > 0) {
                        MultiTransformation(
                            CenterCrop(),
                            GranularRoundedCorners(addTopCorners, addTopCorners, 0f, 0f)
                        )
                    } else
                        FitCenter()
                })
            .into(img)
    }

    override fun <T> AppCompatImageView.loadImageWithBlurEffect(model: T) {
        Glide.with(context).load(model)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(41, 5)))
            .into(this)
        return
    }

    override fun setDrawable(appCompatImageView: AppCompatImageView, res: Int) {
        appCompatImageView.setImageResource(res)
    }



    override fun setImageViewTint(imageView: ImageView, tint: Int) {
        val wrapDrawable = DrawableCompat.wrap(imageView.drawable)
        DrawableCompat.setTint(wrapDrawable, tint)
        DrawableCompat.setTintMode(wrapDrawable, PorterDuff.Mode.SRC_IN)
        imageView.setImageDrawable(wrapDrawable)
    }

    override fun setTextViewResString(txtView: MaterialTextView, res: Int?) {
        if (res == null || res == 0) return

        txtView.setText(res)
    }
}