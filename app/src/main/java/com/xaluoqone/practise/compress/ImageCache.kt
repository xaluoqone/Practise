package com.xaluoqone.practise.compress

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import com.jakewharton.disklrucache.DiskLruCache
import com.xaluoqone.practise.BuildConfig
import java.io.File
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.Collections
import kotlin.concurrent.thread

class ImageCache private constructor() {
    private lateinit var context: Context
    private lateinit var memoryCache: LruCache<String, Bitmap>
    private lateinit var diskLruCache: DiskLruCache

    // 实际上要复用的是 bitmap 所占用的内存，而非 bitmap 本身
    private lateinit var reusableBitmaps: MutableSet<WeakReference<Bitmap>>
    private lateinit var referenceQueue: ReferenceQueue<Bitmap>
    private val bitmapOptions = BitmapFactory.Options()

    companion object {
        val instance by lazy { ImageCache() }
    }

    fun init(context: Context, cacheDir: String) {
        this.context = context.applicationContext
        // 初始化 bitmap 复用池
        reusableBitmaps = Collections.synchronizedSet(hashSetOf())
        // 初始化引用队列
        initReferenceQueue()
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // 获取 app 最大可用内存, 单位为 M
        val maxMemory = am.memoryClass
        val cacheSize = maxMemory * 1024 * 1024 / 8
        // 创建内存缓存, 参数为缓存大小, 单位为 byte
        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            // 返回单个Bitmap占用的内存
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.allocationByteCount
            }

            // 当LRU中的元素被移除时调用
            override fun entryRemoved(evicted: Boolean, key: String, oldValue: Bitmap, newValue: Bitmap?) {
                // 如果是可复用的 bitmap
                if (oldValue.isMutable) {
                    // 将其放入复用池
                    reusableBitmaps.add(WeakReference(oldValue, referenceQueue))
                } else {
                    oldValue.recycle()
                }
            }
        }
        // 创建磁盘缓存, 最大缓存 10M
        diskLruCache = DiskLruCache.open(File(cacheDir), BuildConfig.VERSION_CODE, 1, 100 * 1024 * 1024)
    }

    private fun initReferenceQueue() {
        referenceQueue = ReferenceQueue()
        thread {
            while (true) {
                // 使用阻塞的方式获取引用, 如果内存中没有引用，会一直阻塞这行
                val reference = referenceQueue.remove()
                val bitmap = reference.get()
                if (bitmap?.isRecycled == false) {
                    bitmap.recycle()
                }
            }
        }
    }

    /**
     * 获取图片, 先从内存中获取, 内存中没有再从磁盘中获取
     */
    fun getBitmap(key: String, width: Int, height: Int, inSampleSize: Int): Bitmap? {
        var bitmap = getBitmapFromMemory(key)
        if (bitmap == null) {
            val reusableBitmapMemory = getReusableBitmap(width, height, inSampleSize)
            bitmap = getBitmapFromDisk(key, reusableBitmapMemory)
        }
        return bitmap
    }

    fun putBitmap(key: String, bitmap: Bitmap) {
        putBitmapToMemory(key, bitmap)
        putBitmapToDisk(key, bitmap)
    }

    private fun putBitmapToMemory(key: String, bitmap: Bitmap) {
        memoryCache.put(key, bitmap)
    }

    private fun getBitmapFromMemory(key: String): Bitmap? {
        return memoryCache.get(key)
    }

    fun clearMemoryCache() {
        memoryCache.evictAll()
    }

    private fun getReusableBitmap(width: Int, height: Int, inSampleSize: Int): Bitmap? {
        val iterator = reusableBitmaps.iterator()
        while (iterator.hasNext()) {
            iterator.next().get()?.let { bitmap ->
                if (bitmap.isMutable) {
                    if (bitmapCanReuse(bitmap, width, height, inSampleSize)) {
                        iterator.remove()
                        return bitmap
                    }
                } else {
                    iterator.remove()
                    bitmap.recycle()
                }
            }
        }
        return null
    }

    private fun bitmapCanReuse(bitmap: Bitmap, width: Int, height: Int, inSampleSize: Int): Boolean {
        val outWidth = width / inSampleSize
        val outHeight = height / inSampleSize
        val needMemorySize = outWidth * outHeight * bitmap.getPixelSize()
        return bitmap.allocationByteCount >= needMemorySize
    }

    private fun putBitmapToDisk(key: String, bitmap: Bitmap) {
        diskLruCache.get(key).use { snapshot ->
            // 如果磁盘中没有这个 key 的缓存，就缓存一个
            if (snapshot == null) {
                diskLruCache.edit(key)?.let { editor ->
                    editor.newOutputStream(0).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                        editor.commit()
                    }
                }
            }
        }
    }

    private fun getBitmapFromDisk(key: String, reusableBitmap: Bitmap?): Bitmap? {
        val snapshot = diskLruCache.get(key) ?: return null
        return snapshot.getInputStream(0).use { inputStream ->
            // 设置 bitmap 为可复用
            bitmapOptions.inMutable = true
            // 复用 reusableBitmap 的内存
            bitmapOptions.inBitmap = reusableBitmap
            BitmapFactory.decodeStream(inputStream, null, bitmapOptions).apply {
                // 将 bitmap 放入内存缓存
                if (this != null) {
                    putBitmapToMemory(key, this)
                }
            }
        }
    }
}

fun Bitmap.getPixelSize(): Int {
    return when (config) {
        Bitmap.Config.ARGB_8888 -> 4
        Bitmap.Config.RGB_565, Bitmap.Config.ARGB_4444 -> 2
        Bitmap.Config.ALPHA_8 -> 1
        else -> 1
    }
}