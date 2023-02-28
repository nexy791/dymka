package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.data.utils.moshi.Adapters
import com.ribsky.domain.repository.SaveRepository

class SaveRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : SaveRepository {

    override fun isWordAdded(wordId: String): Boolean = wordId in getWordsList()

    override fun addWord(wordId: String) {
        addWordShared(wordId)
    }

    override fun setWordsList(words: List<String>) {
        saveWordsList(words)
    }

    override fun deleteWord(wordId: String) {
        deleteWordShared(wordId)
    }

    override fun getWordsList(): List<String> {
        val json = sharedPreferences.getString(KEY_WORDS, null) ?: ""
        return runCatching {
            val model = Adapters.saved.fromJson(json)!!
            model
        }.getOrNull() ?: emptyList()
    }

    override fun getWordsIds(): List<String> = getWordsList()

    private fun addWordShared(wordId: String) {
        val list = getWordsList().toMutableList()
        list.add(wordId)
        saveWordsList(list)
    }

    private fun deleteWordShared(wordId: String) {
        val list = getWordsList().toMutableList()
        list.remove(wordId)
        saveWordsList(list)
    }

    private fun saveWordsList(list: List<String>) {
        val json = Adapters.saved.toJson(list)
        sharedPreferences.edit {
            putString(KEY_WORDS, json)
        }
    }

    companion object {
        private const val KEY_WORDS = "WORDS"
    }
}
