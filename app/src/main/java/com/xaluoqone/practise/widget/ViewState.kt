package com.xaluoqone.practise.widget

import android.view.View
import kotlin.reflect.KProperty

interface ViewState<T> {
    var value: T
    operator fun component1(): T
    operator fun component2(): (T) -> Unit
}

operator fun <T> ViewState<T>.setValue(view: View, property: KProperty<*>, value: T) {
    if (this.value != value) {
        this.value = value
        view.invalidate()
    }
}

operator fun <T> ViewState<T>.getValue(view: View, property: KProperty<*>): T = value

class ViewStateImpl<T>(override var value: T) : ViewState<T> {
    override fun component1(): T = value
    override fun component2(): (T) -> Unit = { value = it }
}

fun <T> viewStateOf(t: T): ViewStateImpl<T> {
    return ViewStateImpl(t)
}