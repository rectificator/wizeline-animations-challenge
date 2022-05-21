package com.wizeline.academy.animations.ui.components

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.core.content.withStyledAttributes
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.wizeline.academy.animations.R

class ScalableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

    ) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    private var url: String = ""

    private var mScaleFactor: Float = 1.0f


    private val springForce: SpringForce by lazy {
        SpringForce(mScaleFactor).apply {
            stiffness = SpringForce.STIFFNESS_LOW
            dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        }
    }

    private val imgAnimationScaleX by lazy {
        SpringAnimation(this, DynamicAnimation.SCALE_X).apply {
            spring = springForce
        }
    }
    private val imgAnimationScaleY by lazy {
        SpringAnimation(this, DynamicAnimation.SCALE_Y).apply {
            spring = springForce
        }
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.ScalableImageView) {

            url = getString(R.styleable.ScalableImageView_url).toString()
        }

    }
    init {
        this@ScalableImageView.scaleType = ScaleType.FIT_XY
    }

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            mScaleFactor *= detector.scaleFactor


            // Don't let the object get too small or too large.
            mScaleFactor = 0.1f.coerceAtLeast(mScaleFactor.coerceAtMost(1.5f))
            this@ScalableImageView.scaleX = mScaleFactor
            this@ScalableImageView.scaleY = mScaleFactor

            invalidate()
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            super.onScaleBegin(detector)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            super.onScaleEnd(detector)
            invalidate()

        }
    }

    private val mScaleDetector = ScaleGestureDetector(context, scaleListener)

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(event)

        when (event?.action) {
            MotionEvent.ACTION_BUTTON_PRESS -> performClick()
            MotionEvent.ACTION_POINTER_UP -> {

                springForce.finalPosition = mScaleFactor + 0.2f
                imgAnimationScaleX.start()
                imgAnimationScaleY.start()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
        }

        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            save()
            scale(mScaleFactor,mScaleFactor)

            // onDraw() code goes here
            //this@ScalableImageView.scaleX = mScaleFactor
            //this@ScalableImageView.scaleY = mScaleFactor
            restore()
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }


}