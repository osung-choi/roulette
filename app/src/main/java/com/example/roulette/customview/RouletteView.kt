package com.example.roulette.customview

import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import com.example.roulette.R
import com.example.roulette.repository.database.entity.RouletteItem
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.AnimRes
import androidx.annotation.InterpolatorRes
import com.example.roulette.repository.Utils
import java.util.*
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class RouletteView: View, View.OnTouchListener {
    private val tag = "RouletteView"
    private val sectorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var menu = arrayListOf<RouletteItem>()
    private val colors = arrayListOf(context.getColor(R.color.main_color),
        context.getColor(R.color.sub_color),
        context.getColor(R.color.colorAccent))

    private var mCurrAngle = 0.0
    private var mAddAngle = 0.0

    private var angleQueue: Queue<Double> = LinkedList()

    private var mDirection = false

    private var endCallback: ((String) -> Unit)? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr

    )

    init {
        sectorPaint.style = Paint.Style.FILL_AND_STROKE
        sectorPaint.isAntiAlias = true
        sectorPaint.textAlign = Paint.Align.CENTER

        arrowPaint.color = Color.BLACK
        arrowPaint.strokeWidth = 5F
        arrowPaint.style = Paint.Style.STROKE

        textPaint.color = Color.WHITE
        textPaint.textSize = 40F
        textPaint.style = Paint.Style.FILL_AND_STROKE

        linePaint.color = Color.GRAY
        linePaint.strokeWidth = 0F

        setOnTouchListener(this)
    }

    fun initMenu(menu: ArrayList<RouletteItem> ) {
        if(menu.size > 0) {
            this.menu = menu
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(Utils.dpToPx(context, 400), Utils.dpToPx(context, 400))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val count = menu.size
        val theta = (360.toDouble() / count)
        var temp = 270.toDouble()

        val rectF = RectF(50F, 50F, width.toFloat()-50, height.toFloat()-50)

        val rect = Rect(0, 0, width, height)

        val centerX = ((rect.left + rect.right) / 2).toFloat()
        val centerY = ((rect.top + rect.bottom) / 2).toFloat()
        val radius = (rect.right - rect.left) / 2

        var x1 = centerX-150
        var y1 = 50F
        var x2 = centerX+150
        var y2 = 50F

        val path = Path()
        path.reset()
        path.moveTo(x1, y1)
        path.cubicTo(centerX-80, 10F, centerX+80, 10F, x2, y2)

        canvas?.drawPath(path, arrowPaint)

        canvas?.drawLine(x1, y1, x1+15, y1-30, arrowPaint)
        canvas?.drawLine(x1, y1, x1+30, y1+10, arrowPaint)

        for(i in 0 until count) {
            sectorPaint.color = colors[i%colors.size]

            if(count % colors.size == 1 && i == count-1) {
                sectorPaint.color = colors[1]
            }

            canvas?.drawArc(rectF, temp.toFloat(), theta.toFloat(), true, sectorPaint)

            val medianAngle = temp + theta/2
            val radian = Math.toRadians(medianAngle)

            val textX = centerX + (radius * 0.6 * cos(radian)).toFloat()
            val textY = centerY + (radius * 0.6 * sin(radian)).toFloat()
            
            canvas?.drawText(menu[i].name, textX - menu[i].name.length * 15, textY, textPaint)
            temp += theta
            if(temp >= 360) {
                temp -= 360
            }
        }
    }

    fun setEndCallbackListener(endCallback: (String) -> Unit) {
        this.endCallback = endCallback
    }

    fun startDefaultRotate(angle: Float) {
        if(angle == 0F) return
        val rotate = getRotateAnimation(mAddAngle, -angle.toDouble(), android.R.anim.accelerate_decelerate_interpolator, 3000)

        rotate.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                mAddAngle = (360-angle%360).toDouble()
                setResult(mAddAngle)?.let {
                    endCallback?.invoke(it)
                }
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        this.startAnimation(rotate)
    }

    private fun rotate(fromDegrees: Double, toDegrees: Double, duration: Long = 10, callback: ((String) -> Unit)? = null) {
        val rotate = getRotateAnimation(fromDegrees, toDegrees, android.R.anim.decelerate_interpolator, duration)

        rotate.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                setOnTouchListener(this@RouletteView)

                mAddAngle = toDegrees
                mAddAngle = getAbsAngle(mAddAngle)

                setResult(mAddAngle)?.let {
                    callback?.invoke(it)
                }
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })

        startAnimation(rotate)
    }

    private fun getRotateAnimation(fromDegrees: Double, toDegrees: Double, @AnimRes @InterpolatorRes id: Int, duration: Long) =
        RotateAnimation(fromDegrees.toFloat(), toDegrees.toFloat(),
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            interpolator = AnimationUtils.loadInterpolator(
                context,
                id
            )

            this.duration = duration
            fillAfter = true
        }

    private fun setResult(angle: Double): String? {
        val count = menu.size
        return if(count > 0) {
            val theta = 360.toDouble() / count
            val selectIndex = count - (angle / theta).toInt() - 1
            Log.d(tag, "angle : $angle")

            menu[selectIndex].name

        }else {
            null
        }
    }

    private fun getAbsAngle(angle: Double) = (angle % 360 + 360) % 360

    private fun isDirection(prevAngle: Double, currAngle: Double) =
        if(abs(prevAngle-currAngle) < 300 && prevAngle < currAngle) false
        else if(abs(prevAngle-currAngle) > 300 && prevAngle > currAngle) false
        else if(abs(prevAngle-currAngle) < 300 && prevAngle > currAngle) true
        else true

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val centerOfWidth = width / 2.toFloat()
        val centerOfHeight = height / 2.toFloat()
        val x: Float = event.x
        val y: Float = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mCurrAngle = Math.toDegrees(atan2(x - centerOfWidth.toDouble(),centerOfHeight - y.toDouble()))
            }
            MotionEvent.ACTION_MOVE -> {
                val mPrevAngle = mCurrAngle
                mCurrAngle = Math.toDegrees(atan2(x - centerOfWidth.toDouble(), centerOfHeight - y.toDouble()))

                var speedAngle = getAbsAngle(mCurrAngle - mPrevAngle)
                rotate(mAddAngle, mAddAngle + speedAngle)

                mAddAngle += speedAngle
                mAddAngle = getAbsAngle(mAddAngle)

                mDirection = isDirection(mPrevAngle, mCurrAngle)

                if(speedAngle > 0 && mDirection) speedAngle -= 360
                else if(speedAngle < 0 && !mDirection) speedAngle += 360

                angleQueue.checkAdd(speedAngle)
            }
            MotionEvent.ACTION_UP -> {
                setOnTouchListener(null)
                val avrAngle = angleQueue.average()

                if(mDirection) {
                    var rotateAngle: Double

                    val power = when {
                        abs(avrAngle) < 10 -> 100
                        abs(avrAngle) < 15 -> 250
                        abs(avrAngle) < 20 -> 400
                        else -> 600
                    }.also {
                        rotateAngle = (avrAngle * it)
                        if(it != 100) {
                            rotateAngle -= (Utils.getRandom(360))
                        }
                    }

                    rotate(mAddAngle, mAddAngle + rotateAngle, (10 * power).toLong()) {
                        if(abs(avrAngle) >= 10) endCallback?.invoke(it)
                    }

                }else {
                    val rotateAngle = avrAngle * 50
                    rotate(mAddAngle, mAddAngle + rotateAngle, (10 * 100).toLong())
                }

                angleQueue.clear()
            }
        }
        return true
    }

    private fun <T> Queue<T>.checkAdd(element: T) {
        if(size > 8) {
            poll()
        }

        add(element)
    }
}