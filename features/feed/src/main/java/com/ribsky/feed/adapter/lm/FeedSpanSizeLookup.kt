package com.ribsky.feed.adapter.lm

import androidx.recyclerview.widget.GridLayoutManager

class FeedSpanSizeLookup : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return when (position) {
            0 -> 2
            1 -> 2
            2 -> 2
            else -> 1
        }
    }
}
