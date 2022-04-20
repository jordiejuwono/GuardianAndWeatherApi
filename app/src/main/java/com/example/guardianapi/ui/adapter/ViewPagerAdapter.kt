package com.example.guardianapi.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.guardianapi.ui.article.articlebysection.ArticleListSectionFragment
import com.example.guardianapi.ui.article.articlelist.ArticleListFragment
import com.example.guardianapi.ui.main.MainActivity

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        return Fragment()
    }
}