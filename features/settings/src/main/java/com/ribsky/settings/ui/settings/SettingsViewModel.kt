package com.ribsky.settings.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Event
import com.ribsky.domain.usecase.auth.SignOutUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    private val _status: Event<Boolean> = Event()
    val status: LiveData<Boolean> get() = _status

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase.invoke()
            _status.value = true
        }
    }
}
