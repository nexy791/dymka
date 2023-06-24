package com.ribsky.top.ui.streak

import com.ribsky.top.ui.base.BaseTopRecyclerFragment
import com.ribsky.top.ui.base.ViewType
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopStreakFragment : BaseTopRecyclerFragment<TopStreakViewModel>() {

    companion object {
        fun newInstance() = TopStreakFragment()
    }

    override val viewType: ViewType = ViewType.STREAK
    override val viewModel: TopStreakViewModel by viewModel()
}