package com.shamweel.livereaction

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.shamweel.livereaction.databinding.LayoutLiveReactionBinding
import kotlin.random.Random


class LiveReactionView : FrameLayout {

    private var duration: Long = 3500L
    private val scaleDuration : Long = 500
    private val opaqueAlpha = 255
    private var binding: LayoutLiveReactionBinding? = null
    private var maxOnScreenCount = 30

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_live_reaction,
            this,
            true
        )
    }

    /**
     * Can be used to set Max Floating Reactions on Screen at instant of time
     * @param maxOnScreenCount : Maximmum number fo floating reactions on-screen
     */
    fun setOnScreenMaxReactionCount(maxOnScreenCount: Int){
        this.maxOnScreenCount = maxOnScreenCount
    }

    /**
     * Call to perform Floating Reaction
     * @param drawableRes : Drawable Res file to be used to perform Reaction
     * @param isSelf : when true the the onScreenMaxCount is neglected
     * @param duration : The duration to on-screen animation
     */
    fun performLiveReaction(drawableRes: Int, isSelf: Boolean = false, duration: Long? = null) {
        if (!isSelf && !isRoomAvailableForNewReaction()) { return }
        duration?.let { this.duration = it }
        val reactionSourceImageView = binding?.imageviewReactionSource
        val container = binding?.container

        disableAllParentClip(reactionSourceImageView)
        val floatingImageView = getFloatingImageView(drawableRes, container)
        startFlyingAnimation(floatingImageView)
        startFadingAnimation(floatingImageView, container)
    }

    /**
     * Checks whether room is available to add new reaction on-screen
     */
    private fun isRoomAvailableForNewReaction() = (binding?.container?.childCount?:0) <= maxOnScreenCount

    private fun getFloatingImageView(drawableRes: Int, container: ViewGroup?): AppCompatImageView {
        val image = AppCompatImageView(context)
            .apply {
                layoutParams = binding?.imageviewReactionSource?.layoutParams
                setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
                drawable.alpha = opaqueAlpha
            }
        container?.addView(image)
        return image
    }

    /**
     *   1. Scale Animation (500ms) : to scale from 0.2f -> 1.0f during just start
     *   2. TranslateAnimation (duration provided ?: default 3500ms) by RandomYEnd(Height) and RandomXEnd(Range horizontally)
     *   3. RotateAnimation : to provide random angle while translating
     */
    private fun startFlyingAnimation(imageView: AppCompatImageView) {
        val animationSet = AnimationSet(true)

        val screenHeight = context.resources?.displayMetrics?.heightPixels ?: 0
        val rFrom: Float = screenHeight.times(0.5f)
        val rUntil: Float = screenHeight.times(0.7f)
        val randomEndYDelta = Random.nextInt(rFrom.toInt(), rUntil.toInt())
        val randomEndXDelta = Random.nextDouble(-50.0, 50.0)

        //Scale Animation
        val scaleAnimation = ScaleAnimation(
            0.2f, 1f,
            0.2f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 1f
        ).apply {
            duration = scaleDuration
            interpolator = BounceInterpolator()
        }

        //TranslateAnimation
        val animationTranslate: Animation = TranslateAnimation(
            0f,
            randomEndXDelta.toFloat(),
            0f,
            -randomEndYDelta.toFloat()
        ).apply {
            duration = this@LiveReactionView.duration
            interpolator = getRandomInterpolator()
        }


        //RotateAnimation
        //To generate a random angle for each floating heart
        val minAngle = -10 //Left
        val maxAngle = 6 //Right
        val randomStartAngle = Random.nextInt(minAngle, maxAngle)

        val animationRotate: Animation = RotateAnimation(
            0f,
            randomStartAngle.toFloat(),
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            10f //Start Arcs
        ).apply {
            duration = this@LiveReactionView.duration.div(3) //For multi arc path
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
            interpolator = getRandomInterpolator()
        }

        animationSet.apply {
            isFillEnabled = true
            fillAfter = true
            addAnimation(scaleAnimation)
            addAnimation(animationTranslate)
            addAnimation(animationRotate)
        }
        imageView.startAnimation(animationSet)
    }

    private fun startFadingAnimation(imageView: AppCompatImageView?, container: ViewGroup?) {
        val randomDelay = Random.nextLong(0, duration)
        val randomDuration = duration - randomDelay
        imageView?.let {
            it.animate()
                .alpha(0f)
                .setStartDelay(randomDelay)
                .setDuration(randomDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        it.clearAnimation()
                        container?.removeView(it)
                    }
                })
        }
    }

    private fun disableAllParentClip(view: View?) {
        var mView = view
        mView?.parent?.let {
            while (mView?.parent is ViewGroup) {
                val viewGroup = mView?.parent as ViewGroup
                viewGroup.clipChildren = false
                viewGroup.clipToPadding = false
                mView = viewGroup
            }
        }
    }

    /**
     * Can be used to remove all floating Reactions On-Screen
     */
    fun clearReactionView() {
        val childCount = binding?.container?.childCount ?: 0
        if (childCount > 0) {
            binding?.container?.removeViews(1, childCount - 1)
        }
    }

    private fun getRandomInterpolator() = listOf(
        AccelerateDecelerateInterpolator(),
        AccelerateInterpolator(),
        AnticipateInterpolator(),
        AnticipateOvershootInterpolator(),
        DecelerateInterpolator(),
        LinearInterpolator(),
        OvershootInterpolator(),
        FastOutSlowInInterpolator(),
        FastOutLinearInInterpolator()
    ).random()

}