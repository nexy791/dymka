package com.ribsky.domain.usecase.save

import com.ribsky.domain.repository.SaveRepository

interface SaveWordInteractor {

    fun isWordAdded(word: String): Boolean

    fun addWord(word: String)

    fun deleteWord(word: String)

    fun getWordsIds(): List<String>
}

class SaveWordInteractorImpl(
    private val saveRepository: SaveRepository,
) : SaveWordInteractor {

    override fun isWordAdded(word: String): Boolean = saveRepository.isWordAdded(word)

    override fun addWord(word: String) {
        saveRepository.addWord(word)
    }

    override fun deleteWord(word: String) {
        saveRepository.deleteWord(word)
    }

    override fun getWordsIds(): List<String> = saveRepository.getWordsIds()
}
