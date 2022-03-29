package com.xaluoqone.practise.exo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.xaluoqone.practise.widget.linear

class ExoPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            linear {

            }
        )

        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.getWindowInsetsController(window.decorView)?.apply {
            isAppearanceLightStatusBars = true
        }

        window.decorView.setOnApplyWindowInsetsListener { _, windowInsets ->
            val insets = WindowInsetsCompat.toWindowInsetsCompat(windowInsets)
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            window.decorView.setPadding(0, statusBarInsets.top, 0, 0)
            windowInsets
        }
    }
}