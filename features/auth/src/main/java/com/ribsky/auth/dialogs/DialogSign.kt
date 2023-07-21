package com.ribsky.auth.dialogs

import com.ribsky.auth.databinding.DialogSignBinding
import com.ribsky.common.base.BaseSheet

class DialogSign(
    private val callback: Callback
) : BaseSheet<DialogSignBinding>(DialogSignBinding::inflate) {

    fun interface Callback {
        fun onAuth(type: Type)
    }

    enum class Type {
        GOOGLE,
    }


    override fun initViews() = with(binding) {
        loginButtonGoogle.setOnClickListener {
            dismissWithResult(Type.GOOGLE)
        }
    }

    private fun dismissWithResult(type: Type) {
        callback.onAuth(type)
        dismiss()
    }

    override fun initObserves() {

    }

    override fun clear() {

    }

    companion object {
        fun newInstance(callback: Callback) = DialogSign(
            callback = callback
        )
    }

}