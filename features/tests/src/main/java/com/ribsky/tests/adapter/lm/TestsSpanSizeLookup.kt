package com.ribsky.tests.adapter.lm

import androidx.recyclerview.widget.GridLayoutManager

class TestsSpanSizeLookup : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return when (position) {
            0 -> 2
            else -> 1
        }
    }
}
