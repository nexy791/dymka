package com.ribsky.lessons.dialogs.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.domain.usecase.lesson.LessonInteractor
import kotlinx.coroutines.launch

class LessonInfoViewModel(
    private val lessonInteractor: LessonInteractor,
) : ViewModel() {

    private val _lessonsStatus: MutableLiveData<Resource<BaseLessonModel>> = MutableLiveData()
    val lessonStatus: LiveData<Resource<BaseLessonModel>> get() = _lessonsStatus
    fun getLesson(lessonId: String) {
        viewModelScope.launch {
            _lessonsStatus.value = Resource.loading()
            _lessonsStatus.value = Resource.success(lessonInteractor.getLesson(lessonId))
        }
    }
}
