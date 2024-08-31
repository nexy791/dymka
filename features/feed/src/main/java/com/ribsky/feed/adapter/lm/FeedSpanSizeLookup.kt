package com.ribsky.feed.adapter.lm

import androidx.recyclerview.widget.GridLayoutManager

class FeedSpanSizeLookup(
    private val withPromo: Boolean
) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return if (withPromo) {
            when (position) {
                0 -> 2
                1 -> 2
                2 -> 2
                else -> 1
            }
        } else {
            when (position) {
                0 -> 2
                1 -> 2
                2 -> 1
                else -> 1
            }
        }
    }
}
