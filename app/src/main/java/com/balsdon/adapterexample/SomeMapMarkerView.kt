package com.balsdon.adapterexample

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import com.balsdon.adapterexample.adapters.MapMarkerAdapter
import java.lang.Integer.min

class SomeMapMarkerView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), MapMarkerAdapter {
    private var pinX: Float = 0F
    private var pinY: Float = 0F
    private val mapImage: Bitmap by lazy {
        Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.road),
            width,
            height,
            false)
    }

    private val pinImage: Bitmap by lazy {
        Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.pin),
            (min(width, height) * SCALE).toInt(),
            (min(width, height) * SCALE).toInt(),
            false)
    }

    private val backgroundPaint = Paint().apply {
        color = Color.GREEN
    }

    private val pinPaint = Paint().apply {
        isAntiAlias = true
    }

    init {
        setWillNotDraw(false)
    }

    override fun placeMarker(x: Float, y: Float) {
        pinX = x
        pinY = y
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawPaint(backgroundPaint)
            drawBitmap(mapImage, 0F, (height / 2F) - (mapImage.height / 2f), pinPaint)
            drawBitmap(pinImage, pinX - (pinImage.width / 2F), pinY - pinImage.height, pinPaint)
        }
    }

    companion object {
        private const val SCALE = 0.16
    }
}