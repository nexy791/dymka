package com.ribsky.common.utils.chip

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.alias.commonFont
import com.ribsky.common.utils.ext.ViewExt.Companion.dp
import com.ribsky.common.utils.ext.ViewExt.Companion.px

class ChipBuilder {

    companion object {

        private fun Context.createChip(
            text: String,
            id: Int,
            isChecked: Boolean,
            checkable: Boolean = true,
            size: Float = 14f,
            callback: (Boolean) -> Unit,
        ): Chip {
            return Chip(this).apply {
                setText(text)
                setId(id)
                typeface = ResourcesCompat.getFont(this@createChip, commonFont.e_ukraine_regular)
                textSize = size
                isCheckable = checkable
                setChecked(isChecked)
                shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                    .setAllCornerSizes(16.px.toFloat())
                    .build()
                setOnCheckedChangeListener { _, isChecked ->
                    callback(isChecked)
                }
            }
        }

        fun Fragment.createChip(
            text: String,
            id: Int,
            isChecked: Boolean,
            checkable: Boolean = true,
            size: Float = 14f,
            callback: (Boolean) -> Unit,
        ): Chip = requireContext().createChip(text, id, isChecked, checkable, size, callback)

    }

}