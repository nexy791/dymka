package com.ribsky.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.ribsky.common.alias.Inflate
import com.ribsky.common.navigation.Navigation

abstract class BaseFragment<NV : Navigation, VM : Any, VB : ViewBinding>(
    private val inflate: Inflate<VB>,
) : Fragment() {

    abstract val viewModel: VM
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    abstract val navigation: NV

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
        initView()
        initObs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        clear()
    }

    private fun initNavigation() {
        val navController = findNavController()
        navigation.setup(requireActivity() as? AppCompatActivity?, navController)
    }

    abstract fun initView()
    abstract fun initObs()
    abstract fun clear()
}
