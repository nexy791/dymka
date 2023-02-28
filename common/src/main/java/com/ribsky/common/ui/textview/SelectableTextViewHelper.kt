package com.ribsky.common.ui.textview

class SelectableTextViewHelper {
    companion object {
        private var sPunctuations: List<Char>? = listOf(
            ',',
            '.',
            ';',
            '!',
            '"',
            '，',
            '。',
            '！',
            '；',
            '、',
            '：',
            '“',
            '”',
            '?',
            '？'
        )

        fun getEnglishWordIndices(content: String): List<SelectableTextView.Word> {
            val separatorIndices = getSeparatorIndices(content, ' ')
            for (punctuation in sPunctuations!!) {
                separatorIndices.addAll(getSeparatorIndices(content, punctuation))
            }
            separatorIndices.sort()
            val wordInfoList: MutableList<SelectableTextView.Word> = ArrayList()
            var start = 0
            var end: Int
            for (i in separatorIndices.indices) {
                end = separatorIndices[i]
                if (start == end) {
                    start++
                } else {
                    val wordInfo = SelectableTextView.Word(start, end)
                    wordInfoList.add(wordInfo)
                    start = end + 1
                }
            }
            return wordInfoList
        }

        private fun getSeparatorIndices(word: String, ch: Char): MutableList<Int> {
            var pos = word.indexOf(ch)
            val indices: MutableList<Int> = ArrayList()
            while (pos != -1) {
                indices.add(pos)
                pos = word.indexOf(ch, pos + 1)
            }
            return indices
        }
    }
}
