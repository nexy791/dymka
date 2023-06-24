package com.ribsky.test.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.livedata.Resource
import com.ribsky.common.livedata.ResultMapper.Companion.asResource
import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.usecase.save.SaveWordInteractor
import com.ribsky.domain.usecase.score.AddTestScoreUseCase
import com.ribsky.domain.usecase.test.GetTestContentUseCase
import com.ribsky.domain.usecase.test.TestInteractor
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.test.model.WordModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestDetailsViewModel(
    private val getTestContentUseCase: GetTestContentUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val subManager: SubManager,
    private val addTestScoreUseCase: AddTestScoreUseCase,
    private val saveWordInteractor: SaveWordInteractor,
    private val testInteractor: TestInteractor,
) : ViewModel() {

    private var words: List<WordModel> = emptyList()
    private val word: WordModel? get() = wordStatus.value?.data

    private var _testStatus: MutableLiveData<Resource<BaseTestModel>> = MutableLiveData()
    val testStatus: LiveData<Resource<BaseTestModel>> get() = _testStatus

    private val _wordStatus: MutableLiveData<Resource<WordModel>> =
        MutableLiveData()
    val wordStatus: LiveData<Resource<WordModel>> get() = _wordStatus

    private val _userStatus: MutableLiveData<Resource<BaseUserModel>> =
        MutableLiveData()
    val userStatus: LiveData<Resource<BaseUserModel>> get() = _userStatus

    private var score = 0
    fun getScore(): Int = score


    fun getTestInfo(id: String) {
        viewModelScope.launch {
            _testStatus.value = Resource.loading()
            val result = testInteractor.getTest(id)
            _testStatus.value = Resource.success(result)
        }
    }

    fun toggleWord(): Boolean {
        if (word!!.isSaved) {
            dislikeWord(word!!.id)
        } else {
            likeWord(word!!.id)
        }
        word!!.isSaved = !word!!.isSaved
        return word!!.isSaved
    }

    private fun likeWord(id: String) {
        saveWordInteractor.addWord(id)
    }

    private fun dislikeWord(id: String) {
        saveWordInteractor.deleteWord(id)
        words = words.filter { it.id != id }
    }

    fun getProfile() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            _userStatus.value = getUserUseCase.invoke().asResource()
        }
    }

    fun getContent(word: BaseTestModel) {
        viewModelScope.launch {
            val result = when (word.id) {
                "all" -> getContentAll()
                "fav" -> getFavWords()
                else -> getContent(word.content)
            }
            words = result
            generateNewWord()
        }
    }

    private suspend fun getContent(content: String): List<WordModel> {
        var result: List<WordModel> = emptyList()
        val wordResult = getTestContentUseCase.getContent(content)
        wordResult.fold(
            onSuccess = { list ->
                result = list.shuffled().map { WordModel(it) }
            },
            onFailure = { ex ->
                _wordStatus.value = Resource.error(ex)
            }
        )
        return result
    }

    private suspend fun getContentAll(): List<WordModel> {
        var result: List<WordModel> = emptyList()
        val wordResult = getTestContentUseCase.getContentAll()
        wordResult.fold(
            onSuccess = { list ->
                result = list.shuffled().map { WordModel(it) }
            },
            onFailure = { ex ->
                _wordStatus.value = Resource.error(ex)
            }
        )
        return result
    }

    private suspend fun getFavWords(): List<WordModel> {
        val ids = saveWordInteractor.getWordsIds()
        val allWords = getContentAll()
        return allWords.filter { ids.contains(it.id) }.shuffled()
    }

    fun generateNewWord() {
        viewModelScope.launch {
            delay(500)
            if (words.isNotEmpty()) {
                val word = words.shuffled().first().apply {
                    isSaved = saveWordInteractor.isWordAdded(id)
                }
                _wordStatus.value = Resource.success(word)
            } else {
                _wordStatus.value = Resource.loading()
            }
        }
    }

    fun addScore() {
        viewModelScope.launch {
            addTestScoreUseCase.invoke(1)
            score++
        }
    }

    val isSub get() = subManager.isSub()
}
