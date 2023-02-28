package com.ribsky.settings.ui.library

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.common.utils.ext.ActionExt.Companion.openLink
import com.ribsky.settings.R
import com.ribsky.settings.adapter.library.LibraryAdapter
import com.ribsky.settings.databinding.ActivityLibraryBinding
import com.ribsky.settings.utils.Libraries

class LibraryActivity : AppCompatActivity(R.layout.activity_library) {

    private lateinit var binding: ActivityLibraryBinding
    private var mAdapter: LibraryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        initToolbar()
        initAdapter()
        initRecycler()
    }

    private fun initToolbar() {
        binding.toolBar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initAdapter() {
        mAdapter = LibraryAdapter {
            openLink(it.url)
        }.apply {
            submitList(Libraries.list)
        }
    }

    private fun initRecycler() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(this@LibraryActivity)
        adapter = mAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter = null
    }
}
