package com.ribsky.article.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ribsky.article.ui.fragments.base.BaseArticleFragment

class ArticleViewPager(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private var list: List<BaseArticleFragment<*>> = emptyList()

    fun setFragments(list: List<BaseArticleFragment<*>>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]

    fun getFragment(position: Int): BaseArticleFragment<*>? = list.getOrNull(position)

}