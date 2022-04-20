package com.example.guardianapi.ui.article.articlelist

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import com.example.guardianapi.R
import com.example.guardianapi.base.arch.BaseActivity
import com.example.guardianapi.base.arch.BaseFragment
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.Result
import com.example.guardianapi.databinding.FragmentArticleListBinding
import com.example.guardianapi.ui.adapter.ArticleListAdapter
import com.example.guardianapi.ui.webview.WebViewActivity
import com.example.guardianapi.utils.ArticleData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment : BaseActivity<FragmentArticleListBinding, ArticleListViewModel>(
    FragmentArticleListBinding::inflate
), ArticleListContract.View {

    private lateinit var adapter: ArticleListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var page: Int = 1
    private var totalPage: Int = 1

    override fun initView() {
        getData()
        initList()
        initSwipeRefresh()
        addPagination()
        supportActionBar?.title = getString(R.string.text_title_recent_news)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getData() {
        getViewModel().getArticleList(page)
    }

    override fun onResume() {
        super.onResume()
        getViewBinding().pbArticleClicked.visibility = View.GONE
    }

    override fun initList() {
        adapter = ArticleListAdapter(object : ArticleListAdapter.ArticleItemClickListener {
            override fun onItemClick(items: Result) {
                getViewBinding().pbArticleClicked.visibility = View.VISIBLE
                val intent = Intent(this@ArticleListFragment, WebViewActivity::class.java)
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

        getViewBinding().rvArticles.apply {
            adapter = this@ArticleListFragment.adapter
            layoutManager = this@ArticleListFragment.layoutManager
        }
    }

    override fun addPagination() {

        var loadingFlag = true

        getViewBinding().rvArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    val total = adapter.itemCount
                    if (loadingFlag) {
                        if (page < totalPage) {
                            if ((visibleItemCount + pastVisibleItem) >= total) {
                                loadingFlag = false
                                page++
                                getData()
                                showLoading(false)
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
        getViewBinding().pbNextPage.isVisible = isVisible
    }

    override fun initSwipeRefresh() {
        getViewBinding().srlContent.setOnRefreshListener {
            getViewBinding().srlContent.isRefreshing = false
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