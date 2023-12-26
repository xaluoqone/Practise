package com.xaluoqone.practise.compress

import android.graphics.Bitmap

object NativeCompress {
    init {
        System.loadLibrary("compress")
    }

    fun compressBitmap(bitmap: Bitmap, quality: Int, savePath: String) {
        nativeCompress(bitmap, quality, savePath)
    }

    private external fun nativeCompress(bitmap: Bitmap, quality: Int, savePath: String)
}