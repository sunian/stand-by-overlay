package com.example.stand_by_overlay

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import com.example.stand_by_overlay.ui.OverlayView

class OverlayService : Service() {

    companion object {
        var isRunning = false
    }

    private val overlayView by lazy { OverlayView(this) }

    override fun onCreate() {
        super.onCreate()
        overlayView.layoutParams = WindowManager.LayoutParams(
            TYPE_APPLICATION_OVERLAY,
            FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT,
        )
        val windowManager = getSystemService(WindowManager::class.java)
        if (overlayView.isAttachedToWindow) {
            windowManager.removeView(overlayView)
        }
        windowManager.addView(overlayView, overlayView.layoutParams)
        isRunning = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (overlayView.isAttachedToWindow) {
            val windowManager = getSystemService(WindowManager::class.java)
            windowManager.removeView(overlayView)
        }
        isRunning = false
    }

    override fun onBind(intent: Intent?): IBinder? = null
}