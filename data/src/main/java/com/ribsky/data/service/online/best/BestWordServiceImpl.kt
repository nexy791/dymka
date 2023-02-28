package com.ribsky.data.service.online.best

import android.content.SharedPreferences
import com.google.firebase.firestore.FirebaseFirestore
import com.ribsky.data.model.BestWordApiModel
import com.ribsky.data.service.file.FileService
import com.ribsky.data.service.offline.best.BestWordDao
import com.ribsky.data.utils.crypto.CryptoManager
import com.ribsky.data.utils.moshi.Adapters
import kotlinx.coroutines.tasks.await
import java.util.*

class BestWordServiceImpl(
    private val db: FirebaseFirestore,
    private val fileService: FileService,
    private val cryptoManager: CryptoManager,
    private val sharedPreferences: SharedPreferences,
    private val bestWordDao: BestWordDao,
) : BestWordService {

    override suspend fun loadWords(): Result<List<BestWordApiModel>> {
        val wordsResult = runCatching {
            val request = db.collection("words")
                .document("words")
                .get()
                .await()

            val link = request.data!!["link"]!!.toString()
            val words = fileService.downloadAndGetFile(link).getOrNull()
                ?.let { file ->
                    val json = cryptoManager.decryptFile(file)
                    Adapters.bestWords.fromJson(json)
                }!!
            words
        }
        return wordsResult
    }

    override suspend fun getCurrentBestWord(): BestWordApiModel {
        val id = getWordIdFromSharedPref()
        if (!isCurrentWordNeedUpdate() && id != null) {
            return bestWordDao.get(id)
        } else {
            val newId = bestWordDao.get().shuffled().first().id
            saveWordIdToSharedPref(newId)
        }
        return getCurrentBestWord()
    }

    private fun saveWordIdToSharedPref(id: Int) {
        sharedPreferences.edit().putInt(WORD, id).apply()
    }

    private fun getWordIdFromSharedPref(): Int? = runCatching {
        val id = sharedPreferences.getInt(WORD, -1)
        return@runCatching if (id == -1) null else id
    }.getOrNull()

    private fun isCurrentWordNeedUpdate(): Boolean {
        val lastUpdate = sharedPreferences.getLong(WORD_LAST_UPDATE, 0)
        val currentTime = Date().time
        val isNeedUpdate = currentTime - lastUpdate > 3600000
        if (isNeedUpdate) {
            sharedPreferences.edit().putLong(WORD_LAST_UPDATE, currentTime).apply()
        }
        return isNeedUpdate
    }

    companion object {
        private const val WORD_LAST_UPDATE = "word_last_update"
        private const val WORD = "word"
    }
}
