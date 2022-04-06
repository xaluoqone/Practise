package com.xaluoqone.practise.widget

import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

context(ViewGroup)
fun button(scope: MaterialButton.() -> Unit) =
    addView(MaterialButton(context).apply {
        scope()
    })

context(ViewGroup)
fun text(scope: MaterialTextView.() -> Unit) =
    addView(MaterialTextView(context).apply {
        scope()
    })

const val match = ViewGroup.LayoutParams.MATCH_PARENT
const val wrap = ViewGroup.LayoutParams.WRAP_CONTENT