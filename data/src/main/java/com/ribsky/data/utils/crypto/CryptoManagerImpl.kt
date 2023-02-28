package com.ribsky.data.utils.crypto

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File

class CryptoManagerImpl(
    private val context: Context,
) : CryptoManager {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    override fun encryptFile(file: File) {
        val encryptedFile = EncryptedFile.Builder(
            context,
            file,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val inputStream = file.inputStream()
        file.delete()

        encryptedFile.openFileOutput().use {
            inputStream.copyTo(it)
        }
    }

    override fun decryptFile(file: File): String {
        val encryptedFile = EncryptedFile.Builder(
            context,
            file,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
        return encryptedFile.openFileInput().bufferedReader().use { it.readText() }
    }
}
