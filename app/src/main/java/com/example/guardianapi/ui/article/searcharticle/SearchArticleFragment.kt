package com.example.guardianapi.ui.article.searcharticle

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guardianapi.base.arch.BaseFragment
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import com.example.guardianapi.data.network.model.guardianapi.Result
import com.example.guardianapi.databinding.FragmentSearchArticleBinding
import com.example.guardianapi.ui.adapter.ArticleListAdapter
import com.example.guardianapi.ui.webview.WebViewActivity
import com.example.guardianapi.utils.ArticleData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchArticleFragment :
    BaseFragment<FragmentSearchArticleBinding, SearchArticleViewModel>(FragmentSearchArticleBinding::inflate),
    SearchArticleContract.View {

    private lateinit var adapter: ArticleListAdapter
    private lateinit var layoutManager : LinearLayoutManager
    private var page: Int = 1
    private var totalPage: Int = 1

    override fun initView() {
        getData()
        initList()
        initSwipeRefresh()
        addPagination()
        setOnScrollListener()
    }

    override fun getData() {
        if (getViewBinding().etSearchArticle.text!!.isNotEmpty()){
            getViewModel().getSearchedArticleList(getViewBinding().etSearchArticle.text.toString(), page)
        }
        setOnScrollListener()
    }


    override fun setOnScrollListener(){
        var job: Job? = null
        getViewBinding().etSearchArticle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                job?.cancel()
                job = MainScope().launch {
                    delay(1000L)
                    getViewBinding().lottieNoResults.visibility = View.GONE
                    getViewBinding().tvNoResults.visibility = View.GONE
                    if (getViewBinding().etSearchArticle.text!!.isNotEmpty()) {
                        getViewBinding().ivNoSearch.visibility = View.GONE
                        getViewBinding().tvNoSearch.visibility = View.GONE
                        showLoading(true)
                        getViewModel().getSearchedArticleList(getViewBinding().etSearchArticle.text.toString(), page)
                        initList()
                    } else if(getViewBinding().etSearchArticle.text!!.isEmpty()){
                        showContent(false)
                        getViewBinding().ivNoSearch.visibility = View.VISIBLE
                        getViewBinding().tvNoSearch.visibility = View.VISIBLE
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    override fun handleNoResults(data : GuardianApiResponse){
        if (data.response?.total == 0) {
            getViewBinding().lottieNoResults.visibility = View.VISIBLE
            getViewBinding().lottieNoResults.playAnimation()
            getViewBinding().tvNoResults.visibility = View.VISIBLE
        }
    }

    override fun addPagination(){
        getViewBinding().rvSearchArticles.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount
                if (page < totalPage) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        getViewBinding().pbNextPage.visibility = View.VISIBLE
                        page++
                        getData()
                        showContent(true)
                        showLoading(false)
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getViewBinding().pbArticleClicked.visibility = View.GONE
        initView()
    }

    override fun initList() {
        adapter = ArticleListAdapter(object : ArticleListAdapter.ArticleItemClickListener {
            override fun onItemClick(items: Result) {
                getViewBinding().pbArticleClicked.visibility = View.VISIBLE
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra(ArticleData.WEB_VIEW_URL, items.webUrl)
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
        layoutManager = LinearLayoutManager(requireContext())

        getViewBinding().rvSearchArticles.apply {
            adapter = this@SearchArticleFragment.adapter
            layoutManager = this@SearchArticleFragment.layoutManager
        }
    }

    override fun setDataAdapter(data: List<Result>) {
        data.let { result ->
            adapter.setItems(result)
        }
    }

    override fun initSwipeRefresh() {
        getViewBinding().srlContent.setOnRefreshListener {
            getViewBinding().srlContent.isRefreshing = false
            getData()
        }
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().shimmerLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().srlContent.isVisible = isVisible
    }

    override fun observeData() {
        getViewModel().getSearchedArticleListLiveData().observe(this) { response ->
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
                    totalPage = response.data?.response?.pages!!
                    setDataAdapter(response.data.response?.results!!)
                    handleNoResults(response.data)
                    getViewBinding().pbNextPage.visibility = View.GONE
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