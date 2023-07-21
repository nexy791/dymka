package com.ribsky.testing.data.service.file

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.data.service.file.FileService
import com.ribsky.data.service.file.FileServiceImpl
import com.ribsky.data.utils.crypto.CryptoManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class FileServiceImplTest {

    private lateinit var context: Context
    private lateinit var storage: FirebaseStorage
    private lateinit var internetManager: InternetManager
    private lateinit var cryptoManager: CryptoManager
    private lateinit var fileService: FileService

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        storage = mockk(relaxed = true)
        internetManager = mockk(relaxed = true)
        cryptoManager = mockk(relaxed = true)
        fileService = FileServiceImpl(context, storage, internetManager, cryptoManager)
    }

    @Test
    fun downloadAndGetFile_shouldDownloadAndReturnFileWhenFileIsNotExistsAndOnlineAndDownloadSuccess() =
        runTest {
            val content = "content"
            val gsRef = mockk<StorageReference>()
            val file = mockk<File>()
            val task = mockk<FileDownloadTask.TaskSnapshot>()


            every { context.cacheDir } returns mockk {
                every { path } returns "cache-dir"
            }
            every { storage.getReferenceFromUrl(content) } returns gsRef
            every { gsRef.name } returns "file-name"
            every { internetManager.isOnline() } returns true
            every { file.exists() } returns false
            every { cryptoManager.encryptFile(any()) } returns Unit
            coEvery { gsRef.getFile(file).await() } returns task
            every { cryptoManager.encryptFile(file) } just Runs

            val result = fileService.downloadAndGetFile(content)

            assert(result.isSuccess)
            assertEquals(file.path, result.getOrNull()?.path)
            verify { cryptoManager.encryptFile(file) }

        }


}