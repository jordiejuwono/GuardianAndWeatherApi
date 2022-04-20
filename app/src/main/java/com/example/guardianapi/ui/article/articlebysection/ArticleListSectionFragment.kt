package com.example.guardianapi.ui.article.articlebysection

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guardianapi.base.arch.BaseActivity
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.Result
import com.example.guardianapi.databinding.FragmentArticleListSectionBinding
import com.example.guardianapi.ui.adapter.ArticleListAdapter
import com.example.guardianapi.ui.mainfragment.MainFragment
import com.example.guardianapi.ui.webview.WebViewActivity
import com.example.guardianapi.utils.ArticleData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListSectionFragment :
    BaseActivity<FragmentArticleListSectionBinding, ArticleListSectionViewModel>(
        FragmentArticleListSectionBinding::inflate
    ), ArticleListSectionContract.View {

    companion object {
        var section = ""
    }

    private lateinit var adapter: ArticleListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var page: Int = 1
    private var totalPage: Int = 1

    override fun initView() {
        getData()
        initList()
        initSwipeRefresh()
        addPagination()
        setFragmentTitle()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setFragmentTitle() {
        val categoryTitle = intent.getStringExtra(MainFragment.CATEGORY_TITLE)
        supportActionBar?.title = categoryTitle
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        getViewBinding().pbArticleClicked.visibility = View.GONE
    }

    override fun getData() {
        getViewModel().getArticleListBySection(page, section)
    }

    override fun initList() {
        adapter = ArticleListAdapter(object : ArticleListAdapter.ArticleItemClickListener {
            override fun onItemClick(items: Result) {
                getViewBinding().pbArticleClicked.visibility = View.VISIBLE
                val intent = Intent(this@ArticleListSectionFragment, WebViewActivity::class.java)
                intent.putExtra(ArticleData.ARTICLE_ID, items.id)
                intent.putExtra(ArticleData.WEB_VIEW_URL, items.webUrl)
                intent.putExtra(ArticleData.ARTICLE_DATA_TITLE, items.webTitle)
                intent.putExtra(ArticleData.ARTICLE_DATA_DATE, items.articleDate)
                intent.putExtra(ArticleData.ARTICLE_DATA_SECTION_ID, items.sectionId)
                intent.putExtra(ArticleData.ARTICLE_DATA_SECTION_NAME, items.sectionName)
                intent.putExtra(ArticleData.ARTICLE_DATA_IMAGE, items.fields?.thumbnail)
                intent.putExtra(ArticleData.ARTICLE_DATA, items)
                startActivity(intent)
            }

        })
        layoutManager = LinearLayoutManager(this)

        getViewBinding().rvArticlesSection.apply {
            adapter = this@ArticleListSectionFragment.adapter
            layoutManager = this@ArticleListSectionFragment.layoutManager
        }
    }

    override fun addPagination() {

        var loadingFlag = true

        getViewBinding().rvArticlesSection.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    val total = adapter.itemCount
                    if (loadingFlag) {
                        if (page < totalPage) {
                            if (visibleItemCount + pastVisibleItem >= total) {
                                loadingFlag = false
                                page++
                                getData()
                                showContent(true)
                                showLoadingNextPage(true)
                                loadingFlag = true
                            }
                        }
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun showLoadingNextPage(isVisible: Boolean) {
        getViewBinding().pbNextPageSection.isVisible = isVisible
    }

    override fun initSwipeRefresh() {
        getViewBinding().srlContentSection.setOnRefreshListener {
            getViewBinding().srlContentSection.isRefreshing = false
            getData()
            initList()
            showLoading(true)
        }
    }

    override fun setDataAdapter(data: List<Result>) {
        data.let { result ->
            adapter.setItems(result)
        }
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().shimmerLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().srlContentSection.isVisible = isVisible
    }

    override fun observeData() {
        showLoading(true)
        showContent(false)
        getViewModel().getArticleListSectionLiveData().observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showLoadingNextPage(false)
                    showContent(true)
                    showError(false, null)
                    totalPage = response.data?.response?.pages!!
                    setDataAdapter(response.data.response?.results!!)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, response.message)
                }
            }
        }
    }

}