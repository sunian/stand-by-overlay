package com.example.stand_by_overlay.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.view.View
import android.view.WindowInsets
import androidx.core.graphics.set

private val shaders: List<Shader> = 4.let { size ->
    val shaders = arrayListOf<Shader>()
    repeat(size) { row ->
        repeat(size) { col ->
            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ALPHA_8)
            bitmap[row, col] = Color.BLACK
            shaders.add(BitmapShader(bitmap, TileMode.REPEAT, TileMode.REPEAT))
        }
    }
    shaders
}

class OverlayView(context: Context) : View(context) {

    private val darkPaint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        darkPaint.shader = shaders[(System.currentTimeMillis() / 400 % shaders.size).toInt()]
        canvas.drawPaint(darkPaint)
        windowInsetsController?.hide(
            WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
        )
        postInvalidateDelayed(200)
    }
}