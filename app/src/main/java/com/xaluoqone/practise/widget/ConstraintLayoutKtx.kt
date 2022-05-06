package com.xaluoqone.practise.widget

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

fun Activity.constraint(scope: ConstraintLayout.() -> Unit) = newConstraintLayout(scope)

fun Fragment.constraint(scope: ConstraintLayout.() -> Unit) =
    requireActivity().newConstraintLayout(scope)

fun <T : ViewGroup> T.constraint(scope: ConstraintLayout.() -> Unit) =
    context.newConstraintLayout(scope)

private fun Context.newConstraintLayout(scope: ConstraintLayout.() -> Unit) =
    ConstraintLayout(this@Context).apply(scope)

context (ConstraintLayout)
fun View.layoutParams(
    width: Int = 0,
    height: Int = 0,
    config: ConstraintLayout.LayoutParams.() -> Unit
) {
    layoutParams = ConstraintLayout.LayoutParams(width, height).apply(config)
}