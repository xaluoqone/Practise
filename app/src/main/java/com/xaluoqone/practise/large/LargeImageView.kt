package com.xaluoqone.practise.large

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.io.InputStream

class LargeImageView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), GestureDetector.OnGestureListener, DefaultLifecycleObserver {
    private val rect = Rect()
    private val options = BitmapFactory.Options()
    private val gestureDetector = GestureDetector(context, this)
    private val scroller = Scroller(context)
    private var decoder: BitmapRegionDecoder? = null

    private var imageWidth = 0
    private var imageHeight = 0

    private var scale = 0f

    private var width = 0
    private var height = 0

    private var bitmap: Bitmap? = null

    private val matrix = Matrix()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = measuredWidth
        height = measuredHeight
        scale = width.toFloat() / imageWidth
        matrix.setScale(scale, scale)
        rect.set(0, 0, width, (height / scale).toInt())
    }

    fun setSource(inputStream: InputStream) {
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(inputStream, null, options)
        imageWidth = options.outWidth
        imageHeight = options.outHeight
        // 开启复用
        options.inMutable = true
        options.inPreferredConfig = Bitmap.Config.RGB_565
        options.inJustDecodeBounds = false
        // 创建区域解码器
        decoder = BitmapRegionDecoder.newInstance(inputStream, false)

        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        decoder?.let { decoder ->
            if (bitmap?.isMutable == true) {
                options.inBitmap = bitmap
            } else {
                bitmap?.recycle()
            }
            bitmap = decoder.decodeRegion(rect, options)
            bitmap?.let { bitmap ->
                canvas.drawBitmap(bitmap, matrix, null)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        // 如果滑动没有完成，取消滑动
        if (!scroller.isFinished) {
            scroller.forceFinished(true)
        }
        return true
    }

    /**
     * @param e1 第一个触摸点 (Down event)
     * @param e2 当前触摸点
     * @param distanceX x轴移动距离
     * @param distanceY y轴移动距离
     * @return 是否消费了该触摸事件
     */
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        // 上下滑动的时候，需要修改图片显示区域
        rect.offset(0, (distanceY / scale).toInt())
        // 处理边界
        if (rect.top < 0) {
            rect.top = 0
            rect.bottom = (height / scale).toInt()
        }
        if (rect.bottom > imageHeight) {
            rect.bottom = imageHeight
            rect.top = imageHeight - (height / scale).toInt()
        }
        invalidate()
        return true
    }

    /**
     * @param e1 第一个触摸点 (Down event)
     * @param e2 当前触摸点
     * @param velocityX x轴滑动速度 (velocityX像素/秒) 这里的值是反向的
     * @param velocityY y轴滑动速度 (velocityY像素/秒) 这里的值是反向的
     * @return 是否消费了该触摸事件
     */
    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        scroller.fling(
            0,
            rect.top,
            0,
            -velocityY.toInt(),
            0,
            0,
            0,
            imageHeight - (height / scale).toInt()
        )
        return true
    }

    override fun computeScroll() {
        if (scroller.isFinished) return
        // 判断滑动是否完成
        if (scroller.computeScrollOffset()) {
            rect.top = scroller.currY
            rect.bottom = scroller.currY + (height / scale).toInt()
            invalidate()
        }
    }

    override fun onShowPress(e: MotionEvent) {

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        decoder?.recycle()
        decoder = null
        bitmap?.recycle()
        bitmap = null
        options.inBitmap = null
    }
}