package com.xaluoqone.practise.widget

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment

fun Activity.linear(scope: LinearLayoutCompat.() -> Unit) = newLinearLayout(scope)

fun Fragment.linear(scope: LinearLayoutCompat.() -> Unit) = requireActivity().newLinearLayout(scope)

fun <T : ViewGroup> T.linear(scope: LinearLayoutCompat.() -> Unit) = context.newLinearLayout(scope)

private fun Context.newLinearLayout(scope: LinearLayoutCompat.() -> Unit) =
    LinearLayoutCompat(this).apply {
        scope()
    }

context(LinearLayoutCompat)
fun View.layoutParams(
    width: Int = wrap,
    height: Int = wrap,
    config: LinearLayoutCompat.LayoutParams.() -> Unit
) {
    layoutParams = LinearLayoutCompat.LayoutParams(width, height).apply {
        config()
    }
}

context(LinearLayoutCompat)
val vertical: Int
    get() = LinearLayoutCompat.VERTICAL

context(LinearLayoutCompat)
val horizontal: Int
    get() = LinearLayoutCompat.HORIZONTAL