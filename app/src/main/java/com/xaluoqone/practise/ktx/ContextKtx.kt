package com.xaluoqone.practise.ktx

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.xaluoqone.practise.R

/**
 * 获取压缩后的图片
 * @param targetW 目标宽度
 * @param targetH 目标高度
 * @param removeAlpha 是否去除透明度
 * @return 压缩后的图片
 */
fun Context.getCompressBitmap(targetW: Int? = null, targetH: Int? = null, removeAlpha: Boolean = true): Bitmap {
    val options = BitmapFactory.Options()
    // 只获取图片的宽高，不加载图片到内存中
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, R.mipmap.keqing, options)
    // 图片原本的宽高
    val width = options.outWidth
    val height = options.outHeight
    // 指定目标宽高
    val targetWidth = targetW ?: width
    val targetHeight = targetH ?: height
    // 计算缩放比例
    var scale = 1
    while (width / scale > targetWidth || height / scale > targetHeight) {
        scale *= 2
    }
    // 设置缩放比例
    options.inSampleSize = scale
    // 去除透明度
    if (removeAlpha) {
        options.inPreferredConfig = Bitmap.Config.RGB_565
    }
    // 重新加载图片到内存中
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeResource(resources, R.mipmap.keqing, options)
}