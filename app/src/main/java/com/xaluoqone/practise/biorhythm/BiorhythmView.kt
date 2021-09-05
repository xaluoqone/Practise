package com.xaluoqone.practise.biorhythm

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import com.xaluoqone.practise.ex.dp
import com.xaluoqone.practise.getValue
import com.xaluoqone.practise.setValue
import com.xaluoqone.practise.viewPropertyOf
import kotlin.math.*

class BiorhythmView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val region = Region()
    private val sizeRegion = Region()
    private var circleRadius by viewPropertyOf(0f)
    private var circleWidth by viewPropertyOf(42.dp)
    private val nodeRadius = 16.dp
    private val timeNodes = mutableListOf<TimeNode>()
    private lateinit var currentNode: TimeNode

    inner class TimeNode(var x: Float, var y: Float) {
        val bounds: RectF
            get() {
                return RectF(x - nodeRadius, y - nodeRadius, x + nodeRadius, y + nodeRadius)
            }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            resolveSize(200.dp.toInt(), widthMeasureSpec),
            resolveSize(200.dp.toInt(), widthMeasureSpec)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleRadius = w / 2f
        sizeRegion.set(0, 0, w, h)
    }

    override fun onDraw(canvas: Canvas) = canvas.run {
        path.rewind()
        val centerX = width / 2f
        val centerY = height / 2f
        path.addCircle(centerX, centerY, circleRadius, Path.Direction.CCW)
        path.addCircle(centerX, centerY, circleRadius - circleWidth, Path.Direction.CCW)
        path.fillType = Path.FillType.EVEN_ODD
        region.setPath(path, sizeRegion)
        paint.color = "#000000".toColorInt()
        drawPath(path, paint)
        paint.color = "#FFFFFF".toColorInt()
        timeNodes.forEach {
            drawCircle(it.x, it.y, nodeRadius, paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (region.contains(event.x.toInt(), event.y.toInt())) {
                    for (index in timeNodes.size - 1 downTo 0) {
                        if (timeNodes[index].bounds.contains(event.x, event.y)) {
                            currentNode = timeNodes[index]
                            return true
                        }
                    }
                    return false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (this::currentNode.isInitialized) {
                    changeCurrentNodePosition(event.x, event.y)
                }
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return false
    }

    private fun changeCurrentNodePosition(x: Float, y: Float) {
        val centerX = width / 2f
        val centerY = height / 2f
        val offsetX = abs(x - centerX)
        val offsetY = abs(y - centerY)
        val offset = sqrt(offsetX * offsetX + offsetY * offsetY)
        val sinAngle = offsetY / offset
        var angle = asin(sinAngle)
        if (x > centerX) {
            if (y < centerY) {
                angle = Math.toRadians(90.0).toFloat() - angle + Math.toRadians(270.0).toFloat()
            }
        } else if (x < centerX) {
            if (y < centerY) {
                angle += Math.toRadians(180.0).toFloat()
            } else {
                angle = Math.toRadians(90.0).toFloat() - angle + Math.toRadians(90.0).toFloat()
            }
        }
        val radius = (circleRadius - circleWidth + circleRadius) / 2
        val newX = centerX + radius * cos(angle)
        val newY = centerY + radius * sin(angle)
        currentNode.x = newX
        currentNode.y = newY
        invalidate()
    }

    fun addTimeNode() {
        val radius = (circleRadius - circleWidth + circleRadius) / 2
        val angle = (0..359).random()
        val centerX = width / 2f
        val centerY = height / 2f
        //原点坐标为x,y，则x1=x+距离*cos角度，y1=y+距离*sin角度
        val x = centerX + radius * cos(angle.toDouble()).toFloat()
        val y = centerY + radius * sin(angle.toDouble()).toFloat()
        timeNodes.add(TimeNode(x, y))
        invalidate()
    }
}