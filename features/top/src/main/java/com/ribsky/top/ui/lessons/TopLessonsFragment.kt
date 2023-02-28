package com.ribsky.top.ui.lessons

import com.ribsky.top.ui.base.BaseTopRecyclerFragment
import com.ribsky.top.ui.base.ViewType
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopLessonsFragment : BaseTopRecyclerFragment<TopLessonsViewModel>() {

    companion object {
        fun newInstance() = TopLessonsFragment()
    }

    override val viewType: ViewType = ViewType.LESSONS
    override val viewModel: TopLessonsViewModel by viewModel()
}
