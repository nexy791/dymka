package com.ribsky.data.service.file

import android.content.Context
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.data.utils.crypto.CryptoManager
import com.ribsky.domain.exceptions.Exceptions
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.Date

class FileServiceImpl(
    private val context: Context,
    private val storage: FirebaseStorage,
    val internetManager: InternetManager,
    private val cryptoManager: CryptoManager,
) : FileService {

    override suspend fun downloadAndGetFile(content: String): Result<File> = runCatching {
        val gsRef = storage.getReferenceFromUrl(content)
        val localFile = File(context.cacheDir, gsRef.name)
        val isFileExists = localFile.exists()

        if (isFileExists) {
            return if (localFile.isOld() && internetManager.isOnline()) {
                localFile.delete()
                downloadAndGetFile(content)
            } else {
                Result.success(File(localFile.path))
            }
        } else {
            if (!internetManager.isOnline()) return Result.failure(Exceptions.NoInternetException())
            val result = runCatching {
                gsRef.getFile(localFile).await()
            }
            result.fold(
                onSuccess = {
                    cryptoManager.encryptFile(localFile)
                    return Result.success(File(localFile.path))
                },
                onFailure = { ex ->
                    if (localFile.exists()) localFile.delete()
                    return Result.failure(ex)
                }
            )
        }
    }

    override fun isFileExists(content: String): Boolean {
        if (content.isBlank()) return false
        val gsRef = storage.getReferenceFromUrl(content)
        val localFile = File(context.cacheDir, gsRef.name)
        return localFile.exists()
    }

    override suspend fun delete() {
        context.cacheDir.deleteRecursively()
        context.filesDir.deleteRecursively()
    }

    private fun File.isOld(): Boolean {
        val currentDate = Date().time
        val lastModified = lastModified()
        val diff = (currentDate - lastModified) / (1000 * 60 * 60 * 24)
        return diff >= OLD_FILE_DAYS
    }

    private fun isFileOld(file: File): Boolean {
        val currentDate = Date().time
        val lastModified = file.lastModified()
        val diffInDays = (currentDate - lastModified) / (1000 * 60 * 60 * 24) // days
        return diffInDays >= OLD_FILE_DAYS
    }

    companion object {
        const val OLD_FILE_DAYS = 1
    }

}
