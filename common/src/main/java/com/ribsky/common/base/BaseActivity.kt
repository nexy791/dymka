package com.ribsky.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.ribsky.common.R
import com.ribsky.common.alias.InflateActivity
import com.ribsky.common.navigation.Navigation

abstract class BaseActivity<NV : Navigation, VM : ViewModel, VB : ViewBinding>(
    private val inflate: InflateActivity<VB>,
) : AppCompatActivity() {

    protected abstract val viewModel: VM
    protected lateinit var binding: VB
    abstract val navigation: NV

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        initView()
        initObs()
    }

    override fun onDestroy() {
        super.onDestroy()
        clear()
    }

    private fun initNavigation() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment?)?.navController
        navigation.setup(this, navController)
    }

    abstract fun initView()
    abstract fun initObs()
    abstract fun clear()
}
