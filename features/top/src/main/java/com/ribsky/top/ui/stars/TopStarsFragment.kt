package com.ribsky.top.ui.stars

import com.ribsky.top.ui.base.BaseTopRecyclerFragment
import com.ribsky.top.ui.base.ViewType
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopStarsFragment : BaseTopRecyclerFragment<TopStarsViewModel>() {

    companion object {
        fun newInstance() = TopStarsFragment()
    }

    override val viewType: ViewType = ViewType.STARS
    override val viewModel: TopStarsViewModel by viewModel()
}
