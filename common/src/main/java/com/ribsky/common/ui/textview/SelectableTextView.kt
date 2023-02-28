package com.ribsky.common.ui.textview

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.google.android.material.textview.MaterialTextView
import com.ribsky.common.R
import com.ribsky.common.ui.textview.SelectableTextViewHelper.Companion.getEnglishWordIndices
import com.ribsky.common.utils.ext.ResourceExt.Companion.color

class SelectableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialTextView(context, attrs, defStyleAttr) {

    data class Word(
        val start: Int,
        val end: Int,
    )

    private var mText: CharSequence? = null
    private var mBufferType: BufferType? = null
    private var mOnWordClickListener: OnWordClickListener? = null
    private var mSpannableString: SpannableString? = null
    private var mSelectedBackSpan: BackgroundColorSpan? = null
    private var mSelectedForeSpan: ForegroundColorSpan? = null
    private var highlightText: String = ""
    private val selectedColor: Int by lazy {
        context.color(R.color.md_theme_light_primary)
    }

    private var isDisableHighlight: Boolean = false

    fun setDisableHighlight(isDisableHighlight: Boolean) {
        this.isDisableHighlight = isDisableHighlight
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        mText = text
        mBufferType = type
        highlightColor = Color.TRANSPARENT
        movementMethod = LinkMovementMethod.getInstance()
        setText()
    }

    private fun setText() {
        mSpannableString = SpannableString(mText)
        setHighLightSpan(mSpannableString!!)
        dealEnglish()
        super.setText(mSpannableString, mBufferType)
    }

    private fun dealEnglish() {
        val wordInfoList: List<Word> = getEnglishWordIndices(mText.toString())
        for (wordInfo in wordInfoList) {
            mSpannableString!!.setSpan(
                clickableSpan,
                wordInfo.start,
                wordInfo.end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun setHighLightSpan(spannableString: SpannableString) {
        if (TextUtils.isEmpty(highlightText)) {
            return
        }
        var hIndex = mText.toString().indexOf(highlightText)
        while (hIndex != -1) {
            spannableString.setSpan(
                ForegroundColorSpan(highlightColor),
                hIndex,
                hIndex + highlightText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            hIndex += highlightText.length
            hIndex = mText.toString().indexOf(highlightText, hIndex)
        }
    }

    private fun setSelectedSpan(tv: TextView) {
        if (mSelectedBackSpan == null || mSelectedForeSpan == null) {
            mSelectedBackSpan = BackgroundColorSpan(selectedColor)
            mSelectedForeSpan = ForegroundColorSpan(Color.WHITE)
        } else {
            mSpannableString!!.removeSpan(mSelectedBackSpan)
            mSpannableString!!.removeSpan(mSelectedForeSpan)
        }
        mSpannableString!!.setSpan(
            mSelectedBackSpan,
            tv.selectionStart,
            tv.selectionEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mSpannableString!!.setSpan(
            mSelectedForeSpan,
            tv.selectionStart,
            tv.selectionEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        super@SelectableTextView.setText(mSpannableString, mBufferType)
    }

    fun dismissSelected() {
        mSpannableString!!.removeSpan(mSelectedBackSpan)
        mSpannableString!!.removeSpan(mSelectedForeSpan)
        super@SelectableTextView.setText(mSpannableString, mBufferType)
    }

    private val clickableSpan: ClickableSpan
        get() = object : ClickableSpan() {
            override fun onClick(widget: View) {
                if (isDisableHighlight) return
                val tv = widget as TextView
                val startIndex = tv.selectionStart
                val endIndex = tv.selectionEnd
                if (TextUtils.isEmpty(text) || startIndex == -1 || endIndex == -1) {
                    return
                }
                val word = tv
                    .text
                    .subSequence(
                        startIndex,
                        endIndex
                    ).toString()
                setSelectedSpan(tv)
                mOnWordClickListener?.onClick(word)
            }

            override fun updateDrawState(ds: TextPaint) {}
        }

    fun setOnWordClickListener(listener: OnWordClickListener?) {
        mOnWordClickListener = listener
    }

    fun interface OnWordClickListener {
        fun onClick(word: String?)
    }
}
