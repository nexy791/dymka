package com.ribsky.games.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.livedata.Resource
import com.ribsky.common.livedata.ResultMapper.Companion.asResource
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.usecase.file.IsContentExistsUseCase
import com.ribsky.domain.usecase.test.TestInteractor
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.games.model.GameModel
import kotlinx.coroutines.launch

class GamesViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val testInteractor: TestInteractor,
    private val subManager: SubManager,
    private val isContentExistsUseCase: IsContentExistsUseCase,
) : ViewModel() {

    private val _userStatus: MutableLiveData<Resource<BaseUserModel>> = MutableLiveData()
    val userStatus: LiveData<Resource<BaseUserModel>> get() = _userStatus

    private val _testStatus: MutableLiveData<Resource<List<GameModel>>> = MutableLiveData()
    val testStatus: LiveData<Resource<List<GameModel>>> get() = _testStatus

    val user get() = _userStatus.value?.data

    fun isFileExists(content: String) = isContentExistsUseCase.invoke(content)


    fun getProfile() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            _userStatus.value = getUserUseCase.invoke().asResource()
        }
    }

    // TODO: filter for fav test
    fun getTests() {
        viewModelScope.launch {
            _testStatus.value = Resource.loading()
            val list = testInteractor.getTests().map { game ->
                GameModel(game, isTestActive(game.hasPrem))
            }.filter { it.id != "fav" }
            _testStatus.value =
                Resource.success(list.sortedBy { it.sort })
        }
    }

    private fun isTestActive(isTestPrem: Boolean): Boolean {
        if (!isTestPrem) return true
        return isSub
    }

    private val isSub get() = subManager.isSub()
}
