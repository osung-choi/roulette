package com.example.roulette.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import com.example.roulette.R
import com.example.roulette.repository.data.RouletteItem

import kotlin.math.cos
import kotlin.math.sin

class RouletteView: View {
    private val sectorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val initAngle = 10.0
    private var menu = arrayListOf<RouletteItem>()
    private val colors = arrayListOf(context.getColor(R.color.main_color),
        context.getColor(R.color.sub_color),
        context.getColor(R.color.colorAccent))

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

        textPaint.color = Color.WHITE
        textPaint.textSize = 40F
        textPaint.style = Paint.Style.FILL_AND_STROKE

        linePaint.color = Color.GRAY
        linePaint.strokeWidth = 0F
    }

    fun initMenu(menu: ArrayList<RouletteItem> ) {
        if(menu.size > 0) {
            this.menu = menu
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val count = menu.size
        val theta = (360.toDouble() / count)
        var temp = 270.toDouble() - initAngle

        val rectF = RectF(0F, 0F, width.toFloat(), height.toFloat())
        val rect = Rect(0, 0, width, height)

        val centerX = ((rect.left + rect.right) / 2).toFloat()
        val centerY = ((rect.top + rect.bottom) / 2).toFloat()
        val radius = (rect.right - rect.left) / 2

        for(i in 0 until count) {
            sectorPaint.color = colors[i%colors.size]

            if(count % colors.size == 1 && i == count) {
                sectorPaint.color = colors[2]
            }

            canvas?.drawArc(rectF, temp.toFloat(), theta.toFloat(), true, sectorPaint)

            val medianAngle = temp + theta/2
            val radian = Math.toRadians(medianAngle)

            val textX = centerX + (radius * 0.75 * cos(radian)).toFloat()
            val textY = centerY + (radius * 0.75 * sin(radian)).toFloat()
            
            canvas?.drawText(menu[i].name, textX - menu[i].name.length * 15, textY, textPaint)
            temp += theta
            if(temp >= 360) {
                temp -= 360
            }

        }
    }

    fun startRatate(angle: Float, endCallback: (String) -> Unit) {
        if(angle == 0F) return
        val rotateAnimation = RotateAnimation(
            0F, -angle,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotateAnimation.interpolator = AnimationUtils.loadInterpolator(
            this.context,
            android.R.anim.accelerate_decelerate_interpolator
        )
        rotateAnimation.duration = 3000
        rotateAnimation.isFillEnabled = true
        rotateAnimation.fillAfter = true

        rotateAnimation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                setResult(angle)?.let {
                    endCallback.invoke(it)
                }
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        this.startAnimation(rotateAnimation)
    }

    private fun setResult(angle: Float): String? {
        val count = menu.size
        return if(count > 0) {
            val randomAngle = angle % 3600 + initAngle
            val theta = 360.toDouble() / count

            val selectIndex = (randomAngle / theta).toInt() % count
            menu[selectIndex].name
        }else {
            null
        }
    }
}