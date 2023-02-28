package com.ribsky.domain.repository

interface SaveRepository {

    fun isWordAdded(wordId: String): Boolean

    fun addWord(wordId: String)

    fun setWordsList(words: List<String>)

    fun deleteWord(wordId: String)

    fun getWordsList(): List<String>

    fun getWordsIds(): List<String>
}
