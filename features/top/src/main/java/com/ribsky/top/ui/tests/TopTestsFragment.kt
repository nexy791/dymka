package com.ribsky.top.ui.tests

import com.ribsky.top.ui.base.BaseTopRecyclerFragment
import com.ribsky.top.ui.base.ViewType
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopTestsFragment : BaseTopRecyclerFragment<TopTestsViewModel>() {

    companion object {
        fun newInstance() = TopTestsFragment()
    }

    override val viewType: ViewType = ViewType.SCORES
    override val viewModel: TopTestsViewModel by viewModel()
}
