package com.quraanali.discoverPlaces.utils

import android.graphics.*
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.util.Util
import java.nio.ByteBuffer
import java.security.MessageDigest


class RoundedCornersWithBorder(
        val roundingRadius: Int,
        val strokeWidth: Int?,
        @ColorInt val strokeColor: Int?,
) : BitmapTransformation() {

    private val idBytes = ID.toByteArray(CHARSET)

    override fun transform(
            pool: BitmapPool,
            toTransform: Bitmap,
            outWidth: Int,
            outHeight: Int,
    ): Bitmap {

        if ((strokeWidth ?: 0) > 0 && strokeColor != null) {

            val strokePaint = Paint()
            strokePaint.color = strokeColor
            strokePaint.style = Paint.Style.STROKE
            strokePaint.strokeWidth =(strokeWidth?: 0).toFloat() * (toTransform.density / 160f)// convert from dp to px
            strokePaint.isAntiAlias = true


            val newBitmap = Bitmap.createBitmap(toTransform.width, toTransform.height, toTransform.config)
            val strokeRect = RectF(0f, 0f, newBitmap.width.toFloat(), newBitmap.height.toFloat())

            val canvas = Canvas(newBitmap)
            canvas.drawColor(ColorUtils.setAlphaComponent(strokeColor, 70))//background color with alpha
            canvas.drawBitmap(toTransform,0f,0f,null) // draw original image
            canvas.drawRoundRect(strokeRect, roundingRadius.toFloat(), roundingRadius.toFloat(), strokePaint) // draw stroke
            setCanvasBitmapDensity(toTransform, newBitmap)
            return TransformationUtils.roundedCorners(pool, newBitmap, roundingRadius)

        }else{
            val bitmap = TransformationUtils.roundedCorners(pool, toTransform, roundingRadius)
            setCanvasBitmapDensity(toTransform, bitmap)
            return bitmap
        }


    }

    private fun setCanvasBitmapDensity(toTransform: Bitmap, canvasBitmap: Bitmap) {
        canvasBitmap.density = toTransform.density
    }

    override fun equals(other: Any?): Boolean {
        if (other is RoundedCornersWithBorder) {
            return roundingRadius == other.roundingRadius && strokeWidth == other.strokeWidth && strokeColor == other.strokeColor
        }
        return false
    }

    override fun hashCode(): Int {
        return Util.hashCode(ID.hashCode(), Util.hashCode(roundingRadius))
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(idBytes)
        val radiusData = ByteBuffer.allocate(4).putInt(roundingRadius).array()
        messageDigest.update(radiusData)
    }

    companion object {
        private const val ID = "RoundedCornersWithBorder"
    }
}