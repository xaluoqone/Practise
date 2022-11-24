package com.xaluoqone.practise.ex

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

data class InitialPadding(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)

data class InitialMargin(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)

fun View.recordInitialPadding() = InitialPadding(left, top, right, bottom)
fun View.recordInitialMargin(): InitialMargin {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams
    return InitialMargin(
        lp?.leftMargin ?: 0,
        lp?.topMargin ?: 0,
        lp?.rightMargin ?: 0,
        lp?.bottomMargin ?: 0
    )
}

fun View.doOnApplyWindowInsets(block: (insets: WindowInsetsCompat, initialPadding: InitialPadding, initialMargin: InitialMargin) -> Unit) {
    val initialPadding = recordInitialPadding()
    val initialMargin = recordInitialMargin()
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        block(insets, initialPadding, initialMargin)
        insets
    }
    requestApplyInsetsWhenAttached()
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

fun Activity.configSystemBar(config: SystemBarConfig.() -> Unit) {
    val (
        decorFitsSystemWindows,
        statusBarColor,
        navigationBarColor,
        statusBarBlackFont,
        navigationBarBlackIcon,
        gestureNavigationTransparent,
        navigationTransparent,
        applyStatusBarView,
        applyNavigationBarView
    ) = SystemBarConfig().apply(config)
    WindowCompat.setDecorFitsSystemWindows(window, decorFitsSystemWindows)
    window.statusBarColor = statusBarColor
    WindowCompat.getInsetsController(window, window.decorView).run {
        isAppearanceLightStatusBars = statusBarBlackFont
        isAppearanceLightNavigationBars = navigationBarBlackIcon
    }

    window.decorView.doOnApplyWindowInsets { insets, _, _ ->
        val navigationWindowInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
        val isGestureNavigation = isGestureNavigation(navigationWindowInsets)
        if (!isGestureNavigation) {
            val newNavigationWindowInsets = if (navigationTransparent) {
                window.navigationBarColor = Color.TRANSPARENT
                WindowInsetsCompat.Builder()
                    .setInsets(
                        WindowInsetsCompat.Type.navigationBars(), Insets.of(
                            navigationWindowInsets.left, navigationWindowInsets.top,
                            navigationWindowInsets.right, 0
                        )
                    ).build()
            } else {
                window.navigationBarColor = navigationBarColor
                insets
            }
            ViewCompat.onApplyWindowInsets(window.decorView, newNavigationWindowInsets)
        } else if (isGestureNavigation) {
            val newNavigationWindowInsets = if (gestureNavigationTransparent) {
                window.navigationBarColor = Color.TRANSPARENT
                WindowInsetsCompat.Builder()
                    .setInsets(
                        WindowInsetsCompat.Type.navigationBars(), Insets.of(
                            navigationWindowInsets.left, navigationWindowInsets.top,
                            navigationWindowInsets.right, 0
                        )
                    ).build()
            } else {
                window.navigationBarColor = navigationBarColor
                insets
            }
            ViewCompat.onApplyWindowInsets(window.decorView, newNavigationWindowInsets)
        }
    }
    applyStatusBarView?.doOnApplyWindowInsets { insets, _, _ ->
        val statusWindowInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
        applyStatusBarView.updatePadding(top = statusWindowInsets.top)
    }
    applyNavigationBarView?.doOnApplyWindowInsets { insets, _, _ ->
        val navigationWindowInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
        applyNavigationBarView.updatePadding(bottom = navigationWindowInsets.bottom)
    }
}

fun Activity.isGestureNavigation(navigationBarInsets: Insets): Boolean {
    val threshold = (20 * this.resources.displayMetrics.density).toInt()
    return navigationBarInsets.bottom <= threshold.coerceAtLeast(44)
}

class SystemBarConfig {
    var decorFitsSystemWindows = false
    var statusBarColor = Color.TRANSPARENT
    var navigationBarColor = Color.TRANSPARENT
    var statusBarBlackFont = false
    var navigationBarBlackIcon = false
    var gestureNavigationTransparent = true
    var navigationTransparent = false
    var applyStatusBarView: View? = null
    var applyNavigationBarView: View? = null

    operator fun component1() = decorFitsSystemWindows
    operator fun component2() = statusBarColor
    operator fun component3() = navigationBarColor
    operator fun component4() = statusBarBlackFont
    operator fun component5() = navigationBarBlackIcon
    operator fun component6() = gestureNavigationTransparent
    operator fun component7() = navigationTransparent
    operator fun component8() = applyStatusBarView
    operator fun component9() = applyNavigationBarView
}