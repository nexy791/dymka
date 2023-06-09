package com.ribsky.bot.helper.translator

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

class TranslatorHelperImpl() : TranslatorHelper {

    private var ukEnTranslator: Translator? = null
    private var enUkTranslator: Translator? = null

    private var isUkEnModelDownloaded = false
    private var isEnUkModelDownloaded = false
    private val isModelDownloaded get() = isUkEnModelDownloaded && isEnUkModelDownloaded

    override fun init() {
        getClients()
        downloadModel()
    }

    override suspend fun translate(
        text: String,
        language: TranslatorHelper.Language,
    ): Result<String> {
        return if (isModelDownloaded) {
            if (language == TranslatorHelper.Language.UKRAINIAN) {
                runCatching {
                    ukEnTranslator!!.translate(text).await()
                }
            } else {
                runCatching {
                    enUkTranslator!!.translate(text).await()
                }
            }
        } else {
            Result.failure(Exception("Model is not downloaded"))
        }
    }

    private fun getClients() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.UKRAINIAN)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()
        ukEnTranslator = Translation.getClient(options)

        val options2 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.UKRAINIAN)
            .build()
        enUkTranslator = Translation.getClient(options2)
    }

    private fun downloadModel() {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        ukEnTranslator?.downloadModelIfNeeded(conditions)
            ?.addOnSuccessListener {
                isUkEnModelDownloaded = true
            }
            ?.addOnFailureListener { exception ->
            }
        enUkTranslator?.downloadModelIfNeeded(conditions)
            ?.addOnSuccessListener {
                isEnUkModelDownloaded = true
            }
            ?.addOnFailureListener { exception ->
            }
    }
}
