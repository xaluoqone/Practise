package com.xaluoqone.practise.ex

import android.graphics.Color
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.setStatusBarTransparent() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.TRANSPARENT
}

fun FragmentActivity.setStatusBarTextColorIsLight(light: Boolean) {
    //设置状态栏和导航栏字体的颜色
    ViewCompat.getWindowInsetsController(findViewById(android.R.id.content))?.run {
        isAppearanceLightStatusBars = light
    }
}

fun View.applyStatusBarPadding() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
        setPadding(
            paddingLeft,
            statusBarInsets.top,
            paddingRight,
            paddingBottom
        )
        insets
    }
    requestApplyInsets()
}