package com.ribsky.data.utils.crypto

import java.io.File

interface CryptoManager {

    fun encryptFile(file: File)

    fun decryptFile(file: File): String
}
