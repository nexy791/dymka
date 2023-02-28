package com.ribsky.lessons.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.livedata.Resource
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.domain.usecase.active.AddActiveLessonUseCase
import com.ribsky.domain.usecase.file.IsContentExistsUseCase
import com.ribsky.domain.usecase.lesson.LessonInteractor
import com.ribsky.domain.usecase.paragraph.ParagraphInteractor
import kotlinx.coroutines.launch

class LessonsViewModel(
    private val lessonInteractor: LessonInteractor,
    private val paragraphInteractor: ParagraphInteractor,
    private val subManager: SubManager,
    private val addActiveLessonUseCase: AddActiveLessonUseCase,
    private val isContentExistsUseCase: IsContentExistsUseCase,
) : ViewModel() {

    private val _lessonsStatus: MutableLiveData<Resource<List<BaseLessonModel>>> =
        MutableLiveData()
    val lessonsStatus: LiveData<Resource<List<BaseLessonModel>>> get() = _lessonsStatus

    private val _paragraphStatus: MutableLiveData<Resource<BaseParagraphModel>> =
        MutableLiveData()
    val paragraphStatus: LiveData<Resource<BaseParagraphModel>> get() = _paragraphStatus

    fun isFileExists(content: String) = isContentExistsUseCase.invoke(content)

    fun getLessons(id: String) {
        viewModelScope.launch {
            _lessonsStatus.value = Resource.loading()
            val lessons = lessonInteractor.getLessons(id)
            _lessonsStatus.value = Resource.success(lessons.sortedBy { it.sort })
            getParagraph(id)
        }
    }

    private fun getParagraph(id: String) {
        viewModelScope.launch {
            _paragraphStatus.value = Resource.loading()
            val paragraph = paragraphInteractor.getParagraph(id)
            _paragraphStatus.value = Resource.success(paragraph)
        }
    }

    fun updateLesson(id: String) {
        viewModelScope.launch {
            _lessonsStatus.value = Resource.loading()
            addActiveLessonUseCase.invoke(id)
        }
    }

    val isSub get() = subManager.isSub()
}
