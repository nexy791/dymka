package studio.zewei.willy.animationratingbar

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.ribsky.ratingbar.R

/**
 * Created by Willy on 2020/12/01.
 */
class AnimationRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FlexboxLayout(context, attrs, defStyleAttr) {

    private val attributes by lazy {
        context.obtainStyledAttributes(attrs, R.styleable.AnimationRatingBar)
    }

    private val starsTint by lazy {
        attributes.getColor(
            R.styleable.AnimationRatingBar_starsTint,
            ContextCompat.getColor(context, R.color.blue)
        )
    }

    private val emptyIcon by lazy {
        attributes.getDrawable(R.styleable.AnimationRatingBar_emptyIcon)
            ?: ContextCompat.getDrawable(context, R.drawable.ic_star_none)!!
    }
    private val filledIcon by lazy {
        attributes.getDrawable(R.styleable.AnimationRatingBar_filledIcon)
            ?: ContextCompat.getDrawable(context, R.drawable.ic_star_all)!!
    }
    private val halfIcon by lazy {
        attributes.getDrawable(R.styleable.AnimationRatingBar_halfIcon)
            ?: ContextCompat.getDrawable(context, R.drawable.ic_star_half)!!
    }
    private val iconSize by lazy {
        attributes.getDimension(
            R.styleable.AnimationRatingBar_iconSize, context.dpToPx(16).toFloat()
        )
    }
    private val iconSpacingEnd by lazy {
        attributes.getDimension(
            R.styleable.AnimationRatingBar_iconSpacingEnd, context.dpToPx(4).toFloat()
        )
    }
    private val iconSpacingStart by lazy {
        attributes.getDimension(
            R.styleable.AnimationRatingBar_iconSpacingStart, context.dpToPx(4).toFloat()
        )
    }
    private val iconSpacingTop by lazy {
        attributes.getDimension(
            R.styleable.AnimationRatingBar_iconSpacingTop, context.dpToPx(4).toFloat()
        )
    }
    private val iconSpacingBottom by lazy {
        attributes.getDimension(
            R.styleable.AnimationRatingBar_iconSpacingBottom, context.dpToPx(4).toFloat()
        )
    }
    private val isIndicatorBar by lazy {
        attributes.getBoolean(
            R.styleable.AnimationRatingBar_isIndicatorBar,
            false
        )
    }
    private val animationMode by lazy {
        AnimationMode.values()[attributes.getInt(
            R.styleable.AnimationRatingBar_iconAnimationMode, 0
        )]
    }
    private val iconAnimationSpeed by lazy {
        1 / attributes.getFloat(
            R.styleable.AnimationRatingBar_iconAnimationSpeed,
            1f
        ) * if (animationMode == AnimationMode.NONE) 0 else 1
    }

    private val numStars =
        attributes.getInt(R.styleable.AnimationRatingBar_numStars, 5).let { if (it < 0) 0 else it }

    private var rating = attributes.getFloat(R.styleable.AnimationRatingBar_rating, 0f)
        .let { if (it > numStars) numStars.toFloat() else if (it < 0) 0f else it }


    init {
        // Setup FlexboxLayout
        flexWrap = FlexWrap.WRAP

        // Create Stars
        for (i in 0 until numStars) {
            val emptyStar = AppCompatImageView(context).apply {
                setColorFilter(starsTint)
            }

            val lp = LayoutParams(iconSize.toInt(), iconSize.toInt())
            lp.setMargins(
                iconSpacingStart.toInt(),
                iconSpacingTop.toInt(),
                iconSpacingEnd.toInt(),
                iconSpacingBottom.toInt()
            )
            emptyStar.layoutParams = lp
            emptyStar.setImageDrawable(emptyIcon)

            if (!isIndicatorBar) emptyStar.setOnClickListener { setRating(i + 1f) }

            addView(emptyStar)
        }

        runAnimation()
    }

    fun setRating(score: Float) {
        rating = score.let { if (it > numStars) numStars.toFloat() else if (it < 0) 0f else it }
        runAnimation()
    }

    fun getRating() = rating

    private fun runAnimation() {
        // Clear all filled stars.
        for (index in 0 until numStars) {
            (getChildAt(index) as AppCompatImageView).setImageDrawable(emptyIcon)
        }

        // Fill
        val needHalf = rating % 1 != 0f
        for (i in 0..rating.toInt()) {
            if (i < rating.toInt())
                postDelayed({
                    fillIconWithAnimation(getChildAt(i) as AppCompatImageView, filledIcon)
                }, (200 * i * iconAnimationSpeed).toLong())

            if (i == rating.toInt() && needHalf)
                postDelayed({
                    fillIconWithAnimation(getChildAt(i) as AppCompatImageView, halfIcon)
                }, (200 * (i + 1) * iconAnimationSpeed).toLong())
        }
    }

    private fun fillIconWithAnimation(view: AppCompatImageView, icon: Drawable) {
        if (animationMode == AnimationMode.NONE) {
            view.setImageDrawable(icon)
            return
        }

        val iconChange = ValueAnimator.ofFloat(0f, 1f).apply {
            interpolator = LinearInterpolator()
            duration = (500 * iconAnimationSpeed).toLong()
            addUpdateListener { valueAnimator ->
                val currentValue = valueAnimator.animatedValue as Float
                if (currentValue > 0.95f) view.setImageDrawable(icon)
            }
        }

        val jumpUp =
            ObjectAnimator.ofFloat(
                view,
                View.TRANSLATION_Y,
                0f,
                context.dpToPx(-10).toFloat(),
                context.dpToPx(-10).toFloat(),
                context.dpToPx(-10).toFloat(),
                0f
            ).apply {
                interpolator = DecelerateInterpolator()
                duration = (1000 * iconAnimationSpeed).toLong()
            }

        val downUp =
            ObjectAnimator.ofFloat(
                view,
                View.TRANSLATION_Y,
                0f,
                context.dpToPx(10).toFloat(),
                context.dpToPx(10).toFloat(),
                context.dpToPx(-10).toFloat(),
                context.dpToPx(-10).toFloat(),
                0f
            ).apply {
                interpolator = DecelerateInterpolator()
                duration = (1000 * iconAnimationSpeed).toLong()
            }

        val rotation = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f).apply {
            interpolator = LinearInterpolator()
            duration = (1000 * iconAnimationSpeed).toLong()
        }

        val zoom = AnimatorSet().apply {
            val s = 1.6f
            play(ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 0.7f, s, 1f).apply {
                interpolator = LinearInterpolator()
                duration = (1000 * iconAnimationSpeed).toLong()
            }).with(ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 0.7f, s, 1f).apply {
                interpolator = LinearInterpolator()
                duration = (1000 * iconAnimationSpeed).toLong()
            })
        }

        val shake = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 15f, 0f, -15f, 0f).apply {
            repeatCount = 5
            interpolator = DecelerateInterpolator()
            duration = (500 * iconAnimationSpeed).toLong() / repeatCount
        }

        val turnRound = ObjectAnimator.ofFloat(view, View.ROTATION_Y, -180f, 0f).apply {
            interpolator = LinearInterpolator()
            duration = (500 * iconAnimationSpeed).toLong()
        }

        AnimatorSet().apply {
            when (animationMode) {
                AnimationMode.TURN_ROUND_ZOOM_JUMP_UP -> {
                    play(iconChange).with(turnRound).with(zoom).with(jumpUp)
                }

                AnimationMode.TURN_ROUND_ZOOM -> {
                    play(iconChange).with(turnRound).with(zoom)
                }

                AnimationMode.TURN_ROUND_JUMP_UP -> {
                    play(iconChange).with(turnRound).with(jumpUp)
                }

                AnimationMode.TURN_ROUND -> {
                    play(iconChange).with(turnRound)
                }

                AnimationMode.SHAKE_ZOOM_DOWN_UP -> {
                    play(iconChange).with(shake).with(zoom).with(downUp)
                }

                AnimationMode.SHAKE_ZOOM_JUMP_UP -> {
                    play(iconChange).with(shake).with(zoom).with(jumpUp)
                }

                AnimationMode.SHAKE_ZOOM -> {
                    play(iconChange).with(shake).with(zoom)
                }

                AnimationMode.SHAKE_DOWN_UP -> {
                    play(iconChange).with(shake).with(downUp)
                }

                AnimationMode.SHAKE_JUMP_UP -> {
                    play(iconChange).with(shake).with(jumpUp)
                }

                AnimationMode.SHAKE -> {
                    play(iconChange).with(shake)
                }

                AnimationMode.ZOOM_DOWN_UP -> {
                    play(iconChange).with(zoom).with(downUp)
                }

                AnimationMode.ZOOM_JUMP_UP -> {
                    play(iconChange).with(zoom).with(jumpUp)
                }

                AnimationMode.ZOOM -> {
                    play(iconChange).with(zoom)
                }

                AnimationMode.ROTATION_DOWN_UP -> {
                    play(iconChange).with(rotation).with(downUp)
                }

                AnimationMode.ROTATION_JUMP_UP -> {
                    play(iconChange).with(rotation).with(jumpUp)
                }

                AnimationMode.ROTATION -> {
                    play(iconChange).with(rotation)
                }

                AnimationMode.NONE -> {
                }
            }
            start()
        }
    }


    enum class AnimationMode {
        NONE,
        ROTATION,
        ROTATION_JUMP_UP,
        ROTATION_DOWN_UP,
        ZOOM,
        ZOOM_JUMP_UP,
        ZOOM_DOWN_UP,
        SHAKE,
        SHAKE_JUMP_UP,
        SHAKE_DOWN_UP,
        SHAKE_ZOOM,
        SHAKE_ZOOM_JUMP_UP,
        SHAKE_ZOOM_DOWN_UP,
        TURN_ROUND,
        TURN_ROUND_JUMP_UP,
        TURN_ROUND_ZOOM,
        TURN_ROUND_ZOOM_JUMP_UP
    }
}