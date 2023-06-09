package com.ribsky.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.ribsky.common.alias.InflateActivity

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding>(
    private val inflate: InflateActivity<VB>,
) : AppCompatActivity() {

    protected abstract val viewModel: VM
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)
        initView()
        initObs()
    }

    override fun onDestroy() {
        super.onDestroy()
        clear()
    }

    abstract fun initView()
    abstract fun initObs()
    abstract fun clear()
}
