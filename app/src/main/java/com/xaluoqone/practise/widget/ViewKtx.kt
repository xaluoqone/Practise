package com.xaluoqone.practise.widget

import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.xaluoqone.practise.ex.dpi

context(ViewGroup)
fun button(scope: MaterialButton.() -> Unit) =
    MaterialButton(context).apply {
        scope()
    }

context(LinearLayoutCompat)
fun button(text: String, onClick: () -> Unit) = button {
    this.text = text
    layoutParams<LinearLayoutCompat.LayoutParams>(width = match) {
        marginStart = 6.dpi
        marginEnd = 6.dpi
    }

    setOnClickListener {
        onClick()
    }
}

context(ViewGroup)
fun text(
    scope: MaterialTextView.() -> Unit
) = MaterialTextView(context).apply {
    scope()
}

const val match = ViewGroup.LayoutParams.MATCH_PARENT
const val wrap = ViewGroup.LayoutParams.WRAP_CONTENT