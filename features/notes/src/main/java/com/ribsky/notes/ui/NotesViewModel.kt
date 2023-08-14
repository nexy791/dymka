package com.ribsky.notes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.core.Resource
import com.ribsky.domain.model.note.BaseNoteModel
import com.ribsky.domain.model.note.NoteModel
import com.ribsky.domain.usecase.notes.DeleteNoteUseCase
import com.ribsky.domain.usecase.notes.GetNotesUseCase
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val subManager: SubManager,
) : ViewModel() {

    private val _status: MutableLiveData<Resource<List<BaseNoteModel>>> = MutableLiveData()
    val status: MutableLiveData<Resource<List<BaseNoteModel>>> = _status

    fun getNotesForParagraph(paragraphId: String) {
        _status.value = Resource.loading()
        viewModelScope.launch {
            var result = getNotesUseCase.invoke(paragraphId)
            if (result.isEmpty()) result = listOf(
                NoteModel(
                    -1,
                    "Тут поки що нічого немає \uD83D\uDE3F<br><br>Додавай будь-які повідомлення до конспекту, натиснувши на них в уроці",
                    paragraphId
                )
            )
            _status.value = Resource.success(result)
        }
    }

    fun deleteNote(id: Int, paragraphId: String) {
        viewModelScope.launch {
            deleteNoteUseCase.invoke(id)
            getNotesForParagraph(paragraphId)
        }
    }

    val isSub get() = subManager.isSub()

}