package com.ribsky.common.ui.snackbar

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.ribsky.common.R
import com.ribsky.common.databinding.LayoutSnackbarBinding

class CustomSnackbar private constructor(
    val title: String,
    val description: String,
    val image: Int,
    val view: View,
) {

    private constructor(builder: Builder) : this(
        builder.title,
        builder.description,
        builder.image,
        builder.view
    )

    companion object {
        fun create(init: Builder.() -> Unit) = Builder(init).build()
    }

    class Builder private constructor() {

        constructor(init: Builder.() -> Unit) : this() {
            init()
        }

        var title: String = "кіт dymka"
        var description: String = ""
        var image: Int = R.drawable.cat
        lateinit var view: ViewGroup

        fun title(init: Builder.() -> String) = apply { title = init() }

        fun description(init: Builder.() -> String) = apply { description = init() }

        fun image(init: Builder.() -> Int) = apply { image = init() }

        fun viewGroup(init: Builder.() -> ViewGroup) = apply { view = init() }

        fun build() = generateHintView(view, view.context, CustomSnackbar(this))

        private fun generateHintView(
            view: ViewGroup,
            context: Context,
            customSnackbar: CustomSnackbar,
        ): Snackbar {
            val binding = LayoutSnackbarBinding.inflate(LayoutInflater.from(context), view, false)
            val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)

            snackBar.view.apply {
                setBackgroundColor(Color.WHITE)
                background =
                    ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.rounded_snackbar,
                        null
                    )
            }

            with(binding) {
                tvDesc.text = customSnackbar.description
                tvTitle.text = customSnackbar.title
                imageView.setImageResource(customSnackbar.image)
                root.setOnClickListener {
                    snackBar.dismiss()
                }
            }

            val snackBarView = snackBar.view as Snackbar.SnackbarLayout
            snackBarView.addView(binding.root, 0)
            return snackBar
        }
    }
}
