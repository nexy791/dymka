package com.ribsky.testing.mock

import android.app.Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import java.util.concurrent.Executor

class TestTask(private val isSuccess: Boolean) : Task<Boolean>() {
    override fun addOnFailureListener(p0: OnFailureListener): Task<Boolean> {
        p0.onFailure(Exception())
        return TestTask(isSuccess)
    }

    override fun addOnFailureListener(p0: Activity, p1: OnFailureListener): Task<Boolean> {
        p1.onFailure(Exception())
        return TestTask(isSuccess)
    }

    override fun addOnFailureListener(p0: Executor, p1: OnFailureListener): Task<Boolean> {
        p1.onFailure(Exception())
        return TestTask(isSuccess)
    }

    override fun getException(): Exception? {
        return null
    }

    override fun getResult(): Boolean {
        return isSuccess
    }

    override fun <X : Throwable?> getResult(p0: Class<X>): Boolean {
        return isSuccess
    }

    override fun isCanceled(): Boolean {
        return false
    }

    override fun isComplete(): Boolean {
        return true
    }

    override fun addOnSuccessListener(
        p0: Executor,
        p1: OnSuccessListener<in Boolean>,
    ): Task<Boolean> {
        p1.onSuccess(isSuccess)
        return TestTask(isSuccess)
    }

    override fun addOnSuccessListener(
        p0: Activity,
        p1: OnSuccessListener<in Boolean>,
    ): Task<Boolean> {
        p1.onSuccess(isSuccess)
        return TestTask(isSuccess)
    }

    override fun addOnSuccessListener(p0: OnSuccessListener<in Boolean>): Task<Boolean> {
        p0.onSuccess(isSuccess)
        return TestTask(isSuccess)
    }

    override fun addOnCompleteListener(p0: OnCompleteListener<Boolean>): Task<Boolean> {
        p0.onComplete(this)
        return TestTask(isSuccess)
    }

    override fun addOnCompleteListener(
        p0: Activity,
        p1: OnCompleteListener<Boolean>,
    ): Task<Boolean> {
        p1.onComplete(this)
        return TestTask(isSuccess)
    }

    override fun isSuccessful(): Boolean = this.isSuccess
}