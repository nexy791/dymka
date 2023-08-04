package com.ribsky.game.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Event
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.domain.model.word.BaseWordModel
import com.ribsky.domain.usecase.test.GetTestContentUseCase
import com.ribsky.domain.usecase.test.TestInteractor
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.game.model.PayLoadModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val testInteractor: TestInteractor,
    private val getTestContentUseCase: GetTestContentUseCase,
) : ViewModel() {

    private var words: List<BaseWordModel> = emptyList()

    private var isHost: Boolean = false
    private var testId: String = ""

    private val _userHostStatus: MutableLiveData<Resource<PayLoadModel.User>> = MutableLiveData()
    val userHostStatus: LiveData<Resource<PayLoadModel.User>> get() = _userHostStatus
    val userHost: PayLoadModel.User? get() = _userHostStatus.value?.data

    private val _userQuestStatus: MutableLiveData<Resource<PayLoadModel.User>> = MutableLiveData()
    val userQuestStatus: LiveData<Resource<PayLoadModel.User>> get() = _userQuestStatus
    val userQuest: PayLoadModel.User? get() = _userQuestStatus.value?.data

    private val _bookStatus: MutableLiveData<Resource<BaseTestModel>> = MutableLiveData()
    val bookStatus: LiveData<Resource<BaseTestModel>> get() = _bookStatus

    private val _wordStatus: MutableLiveData<Resource<BaseWordModel>> = MutableLiveData()
    val wordStatus: LiveData<Resource<BaseWordModel>> get() = _wordStatus

    private val _payloadStatus: MutableLiveData<PayLoadModel> = Event()
    val payloadStatus: LiveData<PayLoadModel> get() = _payloadStatus

    private val _gameStatus: MutableLiveData<GameStatus> = MutableLiveData(GameStatus.NONE)
    val gameStatus: LiveData<GameStatus> get() = _gameStatus
    private val status: GameStatus get() = _gameStatus.value!!

    private val _scoreHostStatus: MutableLiveData<PayLoadModel.Score> =
        MutableLiveData(PayLoadModel.Score(0))
    val scoreHostStatus: LiveData<PayLoadModel.Score> get() = _scoreHostStatus
    val scoreHost: PayLoadModel.Score? get() = _scoreHostStatus.value

    private val _scoreQuestStatus: MutableLiveData<PayLoadModel.Score> =
        MutableLiveData(PayLoadModel.Score(0))
    val scoreQuestStatus: LiveData<PayLoadModel.Score> get() = _scoreQuestStatus
    val scoreQuest: PayLoadModel.Score? get() = _scoreQuestStatus.value

    fun setHost(isHost: Boolean) {
        this.isHost = isHost
    }

    fun setTestId(id: String) {
        this.testId = id
    }

    fun getProfile() {
        viewModelScope.launch {
            _userHostStatus.value = Resource.loading()
            val result = getUserUseCase.invoke().asResource()
            if (result.isSuccess) {
                _userHostStatus.value = Resource.success(PayLoadModel.User(result.data!!))
                sendUser(userHost!!)
                if (isHost) getBook(testId)
            } else {
                _userHostStatus.value = Resource.error(Exceptions.AuthException())
            }
        }
    }

    private fun getBook(id: String) {
        viewModelScope.launch {
            _bookStatus.value = Resource.loading()
            val test = testInteractor.getTest(id)
            _bookStatus.value = Resource.success(test)
            getContent(test.content)
            if (isHost) sendConfig(PayLoadModel.Config(testId))
        }
    }

    private fun getContent(content: String) {
        viewModelScope.launch {
            val wordResult = getTestContentUseCase.getContent(content)
            wordResult.fold(
                onSuccess = { list ->
                    words = list.shuffled()
                    generateNewWord()
                    when (status) {
                        GameStatus.NONE -> sendReady()
                        GameStatus.OPPONENT_READY -> sendReady()
                        else -> {}
                    }
                },
                onFailure = { ex ->
                    _wordStatus.value = Resource.error(ex)
                }
            )
        }
    }

    private fun generateNewWord() {
        viewModelScope.launch {
            delay(500)
            _wordStatus.value = Resource.success(words.random())
        }
    }

    fun processPayLoad(json: String) {
        when (val payload = PayLoadModel.getPayLoadFromJson(json)) {
            is PayLoadModel.Config -> processConfig(payload)
            is PayLoadModel.Score -> processScore(payload)
            is PayLoadModel.Type -> processType(payload)
            is PayLoadModel.User -> processUser(payload)
            null -> {}
        }
    }

    private fun addScore() {
        _scoreHostStatus.value = _scoreHostStatus.value.apply { this!!.addScore(1) }
        sendScore(scoreHost!!)
    }

    private fun removeScore() {
        _scoreHostStatus.value = _scoreHostStatus.value.apply { this!!.minusScore(1) }
        sendScore(scoreHost!!)
    }

    private fun processUser(user: PayLoadModel.User) {
        _userQuestStatus.value = Resource.success(user)
    }

    private fun processConfig(config: PayLoadModel.Config) {
        val id = config.pickedId
        setTestId(id)
        getBook(id)
    }

    private fun processScore(score: PayLoadModel.Score) {
        _scoreQuestStatus.value = score
    }

    private fun processType(type: PayLoadModel.Type) {
        when (type.type) {
            PayLoadModel.Type.SubType.START -> processStart()
            PayLoadModel.Type.SubType.READY -> processReady()
            PayLoadModel.Type.SubType.FINISH -> processFinish()
        }
    }

    private fun processStart() {
        _gameStatus.value = GameStatus.IN_GAME
    }

    private fun processFinish() {
        _gameStatus.value = GameStatus.FINISHED
    }

    private fun processReady() {
        when (status) {
            GameStatus.FINISHED -> {
                _gameStatus.value = GameStatus.OPPONENT_READY
            }

            GameStatus.NONE -> {
                _gameStatus.value = GameStatus.OPPONENT_READY
                _payloadStatus.value = PayLoadModel.Type(PayLoadModel.Type.SubType.READY)
            }

            GameStatus.YOU_READY -> {
                _gameStatus.value = GameStatus.IN_GAME
                _payloadStatus.value = PayLoadModel.Type(PayLoadModel.Type.SubType.START)
            }

            else -> {}
        }
    }

    private fun sendScore(score: PayLoadModel.Score) {
        _payloadStatus.value = score
    }

    private fun sendFinish() {
        _gameStatus.value = GameStatus.FINISHED
        _payloadStatus.value = PayLoadModel.Type(PayLoadModel.Type.SubType.FINISH)
    }

    private fun sendUser(user: PayLoadModel.User) {
        _payloadStatus.value = user
    }

    private fun sendConfig(config: PayLoadModel.Config) {
        _payloadStatus.value = config
    }

    fun sendReady() {
        when (status) {
            GameStatus.FINISHED -> {
                _gameStatus.value = GameStatus.YOU_READY
                _payloadStatus.value = PayLoadModel.Type(PayLoadModel.Type.SubType.READY)
            }

            GameStatus.NONE -> {
                _gameStatus.value = GameStatus.YOU_READY
                _payloadStatus.value = PayLoadModel.Type(PayLoadModel.Type.SubType.READY)
            }

            GameStatus.OPPONENT_READY -> {
                _gameStatus.value = GameStatus.IN_GAME
                _payloadStatus.value = PayLoadModel.Type(PayLoadModel.Type.SubType.START)
            }

            GameStatus.YOU_READY -> {}
            GameStatus.IN_GAME -> {}
        }
    }

    fun resetScore() {
        _scoreHostStatus.value = PayLoadModel.Score(0)
        _scoreQuestStatus.value = PayLoadModel.Score(0)
    }

    fun processWord(isCorrect: Boolean) {
        generateNewWord()
        if (isCorrect) addScore()
        else removeScore()
    }

    fun finishGame() {
        sendFinish()
    }
}
