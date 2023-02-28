package com.ribsky.common.alias

import android.view.LayoutInflater
import android.view.ViewGroup

typealias commonColor = com.ribsky.common.R.color
typealias commonString = com.ribsky.common.R.string
typealias commonDrawable = com.ribsky.common.R.drawable
typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
typealias InflateActivity<T> = (LayoutInflater) -> T
