package com.ribsky.common.utils.party

import android.content.Context
import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import com.ribsky.common.R
import com.skydoves.balloon.*

class ProfileWelcomeBalloonFactory : Balloon.Factory() {

    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return createBalloon(context) {
            setWidth(BalloonSizeSpec.WRAP)
            setHeight(BalloonSizeSpec.WRAP)
            setText("Знижка для новачків! \uD83C\uDF81")
            setTextColor(Color.parseColor("#FFFFFF"))
            setTextSize(12f)
            setBalloonHighlightAnimation(BalloonHighlightAnimation.SHAKE)
            setTextTypeface(ResourcesCompat.getFont(context, R.font.e_ukraine_bold)!!)
            setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            setArrowSize(10)
            setArrowPosition(0.5f)
            setPadding(12)
            setCornerRadius(16f)
            setBackgroundColorResource(R.color.blue)
            setBalloonAnimation(BalloonAnimation.OVERSHOOT)
            setLifecycleOwner(lifecycle)
            setDismissWhenTouchOutside(false)
            setDismissWhenShowAgain(true)
            setAutoDismissDuration(5000L)
            setDismissWhenLifecycleOnPause(true)
            setDismissWhenClicked(true)

        }
    }
}