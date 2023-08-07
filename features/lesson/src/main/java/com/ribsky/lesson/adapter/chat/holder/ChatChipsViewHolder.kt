package com.ribsky.lesson.adapter.chat.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.ShapeAppearanceModel
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.core.utils.SizeUtils.Companion.px
import com.ribsky.lesson.adapter.chat.ChatAdapter
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.databinding.ItemChatChipsBinding
import com.ribsky.lesson.model.ChatModel
import kotlin.random.Random

class ChatChipsViewHolder(private val binding: ItemChatChipsBinding) :
    BaseViewHolder<ChatModel.Chips>(binding) {
    override fun bind(
        item: ChatModel.Chips,
        callback: ChatAdapter.OnChatClickListener,
    ) = with(binding) {
        textView.text = item.text.parseAsHtml()

        chipGroup.removeAllViews()
        item.chips.forEach {
            chipGroup.addView(generateChip(root.context, it).apply {
                setOnClickListener { _ ->
                    playSound(commonRaw.sound_pick)
                }
            })
        }
        chipGroup.setChildrenEnabled(item.isActive)

        btnNext.setOnClickListener {
            if (item.isActive) {
                callback.onChipsClick(chipGroup.getCheckedChips())
                chipGroup.setChildrenChecked(false)
            }
        }
        btnGetHelp.setOnClickListener {
            val answers = item.chips.joinToString("<br>") { "- $it" }
            callback.onGetCatHelpClick(
                """
                Знайди правильну відповідь або правильні відповіді та поясни, чому саме так:<br><br>
                Запитання:<br>${item.text.parseAsHtml()}<br><br>      
                Варіанти відповідей:<br>$answers
            """.trimIndent()
            )
        }
    }

    private fun ChipGroup.getCheckedChips(): List<String> =
        children.map { it as Chip }.filter { it.isChecked }.map { it.text.toString() }.toList()

    private fun ChipGroup.setChildrenEnabled(enable: Boolean) {
        children.forEach { it.isEnabled = enable }
    }

    private fun ChipGroup.setChildrenChecked(enable: Boolean) {
        children.forEach { it.isSelected = enable }
    }

    private fun generateChip(context: Context, content: String): Chip {
        val chip = Chip(context).apply {
            text = content.parseAsHtml()
            tag = Random.nextInt()
            chipStrokeWidth = 0.px.toFloat()
            shapeAppearanceModel =
                ShapeAppearanceModel().withCornerSize(16.px.toFloat())
        }
        return chip
    }

    companion object {

        fun create(parent: ViewGroup): ChatChipsViewHolder {
            val binding = ItemChatChipsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatChipsViewHolder(binding)
        }
    }
}
