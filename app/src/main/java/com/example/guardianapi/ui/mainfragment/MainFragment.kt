package com.example.guardianapi.ui.mainfragment

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.example.guardianapi.base.arch.BaseFragment
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.Result
import com.example.guardianapi.databinding.FragmentMainBinding
import com.example.guardianapi.ui.adapter.ArticleListAdapter
import com.example.guardianapi.ui.article.articlebysection.ArticleListSectionFragment
import com.example.guardianapi.ui.article.articlelist.ArticleListFragment
import com.example.guardianapi.ui.webview.WebViewActivity
import com.example.guardianapi.utils.ArticleData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>(FragmentMainBinding::inflate),
MainFragmentContract.View {

    companion object {
        const val CATEGORY_TITLE = "CATEGORY_TITLE"
    }

    private lateinit var adapter : ArticleListAdapter

    override fun initView() {
        getData()
        initList()
        setOnRefreshListener()
        setOnClickListenerCategories()
    }

    override fun getData() {
        getViewModel().getArticleList(1)
    }

    override fun setOnRefreshListener() {
        getViewBinding().srlContent.setOnRefreshListener {
            getViewBinding().srlContent.visibility = View.GONE
            getViewBinding().srlContent.isRefreshing = false
            getData()
            observeData()
            initList()
            showLoading(true)
        }
    }

    override fun onResume() {
        super.onResume()
        getViewBinding().pbArticleClicked.visibility = View.GONE
    }

    override fun initList() {
        adapter = ArticleListAdapter(object : ArticleListAdapter.ArticleItemClickListener {
            override fun onItemClick(items: Result) {
                getViewBinding().pbArticleClicked.visibility = View.VISIBLE
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra(ArticleData.WEB_VIEW_URL, items.webUrl)
                intent.putExtra(ArticleData.ARTICLE_ID, items.id)
                intent.putExtra(ArticleData.ARTICLE_DATA_TITLE, items.webTitle)
                intent.putExtra(ArticleData.ARTICLE_DATA_DATE, items.articleDate)
                intent.putExtra(ArticleData.ARTICLE_DATA_SECTION_ID, items.sectionId)
                intent.putExtra(ArticleData.ARTICLE_DATA_SECTION_NAME, items.sectionName)
                intent.putExtra(ArticleData.ARTICLE_DATA_IMAGE, items.fields?.thumbnail)
                intent.putExtra(ArticleData.ARTICLE_DATA, items)
                startActivity(intent)
            }
        })

        getViewBinding().rvRecentNews.apply {
            adapter = this@MainFragment.adapter
        }

        ViewCompat.setNestedScrollingEnabled(getViewBinding().rvRecentNews, false)
    }

    override fun setDataAdapter(data: List<Result>) {
        data.let { result ->
            adapter.setItems(result)
        }
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbProgressBar.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().srlContent.isVisible = isVisible
    }

    override fun observeData() {
        getViewModel().getArticleListLiveData().observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    showError(false, null)
                    setDataAdapter(response.data?.response?.results!!)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, response.message)
                }
            }
        }
    }

    override fun setOnClickListenerCategories() {
        setBindingCategories(getViewBinding().llSports, "sport", "Sports")
        setBindingCategories(getViewBinding().llWorldNews, "world", "World News")
        setBindingCategories(getViewBinding().llLifestyle, "lifeandstyle", "Lifestyle")
        setBindingCategories(getViewBinding().llFood, "food", "Food")
        setBindingCategories(getViewBinding().llFashion, "fashion", "Fashion")
        setBindingCategories(getViewBinding().llTechnology, "technology", "Technology")
        setBindingCategories(getViewBinding().llTravel, "travel", "Travel")
        setBindingCategories(getViewBinding().llArts, "artanddesign", "Art & Design")
        getViewBinding().tvViewAll.setOnClickListener {
            val intent = Intent(requireContext(), ArticleListFragment::class.java)
            startActivity(intent)
        }
        getViewBinding().tvSeeResults.setOnClickListener {
            val intent = Intent(requireContext(), ArticleListFragment::class.java)
            startActivity(intent)
        }
    }

    private fun setBindingCategories(binding: LinearLayout, category: String, categoryTitle: String){
        binding.setOnClickListener {
            ArticleListSectionFragment.section = category
            val intent = Intent(requireContext(), ArticleListSectionFragment::class.java)
            intent.putExtra(CATEGORY_TITLE, categoryTitle)
            startActivity(intent)
        }
    }

}