package com.ribsky.data.service.online.lesson

import com.ribsky.data.model.LessonApiModel

interface LessonService {

    suspend fun loadLessons(): Result<List<LessonApiModel>>
}
