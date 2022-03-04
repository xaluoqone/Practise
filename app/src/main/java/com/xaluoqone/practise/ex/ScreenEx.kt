package com.xaluoqone.practise.ex

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

val Number.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

val Number.dpi: Int
    get() = dp.roundToInt()

val Number.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

val Number.spi: Int
    get() = sp.roundToInt()