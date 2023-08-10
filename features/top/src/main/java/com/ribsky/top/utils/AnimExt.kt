package com.ribsky.top.utils

import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator

class AnimExt {

    companion object {

        fun swapAnimation(view0: View, view1: View) {

            val y0 = view0.y
            val y1 = view1.y

            val anim0 = view0.animate()
                .y(y1)
                .setDuration(500)
                .setInterpolator(OvershootInterpolator())

            val anim1 = view1.animate()
                .y(y0)
                .setDuration(500)
                .setInterpolator(OvershootInterpolator())

            anim0.start()
            anim1.start()

        }

        fun tryToSwapAnimation(view0: View) {

            val y1 = view0.y

            view0.animate()
                .y(y1 - 20)
                .setDuration(500)
                .setInterpolator(BounceInterpolator())
                .withEndAction {
                    view0.animate()
                        .y(y1)
                        .setDuration(500)
                        .setInterpolator(BounceInterpolator())
                        .start()
                }.start()

        }

    }

}