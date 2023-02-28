package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth
import com.ribsky.data.service.file.FileService
import com.ribsky.data.service.offline.active.ActiveLessonDao
import com.ribsky.data.service.offline.best.BestWordDao
import com.ribsky.data.service.offline.lesson.LessonsDao
import com.ribsky.data.service.offline.paragraph.ParagraphDao
import com.ribsky.data.service.offline.tests.TestsDao
import com.ribsky.domain.repository.ClearRepository

class ClearRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val sharedPreferences: SharedPreferences,
    private val fileService: FileService,
    private val bestWordDao: BestWordDao,
    private val lessonsDao: LessonsDao,
    private val testsDao: TestsDao,
    private val paragraphDao: ParagraphDao,
    private val activeLessonDao: ActiveLessonDao,

) : ClearRepository {
    override suspend fun clear() {
        firebaseAuth.signOut()
        fileService.delete()
        bestWordDao.delete()
        lessonsDao.delete()
        testsDao.delete()
        paragraphDao.delete()
        activeLessonDao.delete()
        sharedPreferences.edit { clear() }
    }
}
