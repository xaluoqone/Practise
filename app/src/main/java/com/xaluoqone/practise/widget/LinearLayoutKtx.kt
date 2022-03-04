package com.xaluoqone.practise.widget

import android.app.Activity
import android.content.Context
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

fun LinearLayoutCompat.layoutParams(
    width: Int = wrap,
    height: Int = wrap,
    config: LinearLayoutCompat.LayoutParams.() -> Unit
) = LinearLayoutCompat.LayoutParams(width, height).apply {
    config()
}

val LinearLayoutCompat.vertical: Int
    get() = LinearLayoutCompat.VERTICAL

val LinearLayoutCompat.horizontal: Int
    get() = LinearLayoutCompat.HORIZONTAL