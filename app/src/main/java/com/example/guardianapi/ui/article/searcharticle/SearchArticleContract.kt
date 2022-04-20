package com.example.guardianapi.ui.article.searcharticle

import androidx.lifecycle.LiveData
import com.example.guardianapi.base.arch.BaseContract
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import com.example.guardianapi.data.network.model.guardianapi.Result

interface SearchArticleContract {
    interface View : BaseContract.View {
        fun getData()
        fun initList()
        fun initSwipeRefresh()
        fun setDataAdapter(data: List<Result>)
        fun addPagination()
        fun setOnScrollListener()
        fun handleNoResults(data : GuardianApiResponse)
    }

    interface Repository : BaseContract.Repository {
        suspend fun getSearchedArticleList(querySearch: String, currentPage: Int) : GuardianApiResponse
    }

    interface ViewModel : BaseContract.ViewModel {
        fun getSearchedArticleList(querySearch: String, currentPage: Int)
        fun getSearchedArticleListLiveData() : LiveData<Resource<GuardianApiResponse>>
    }
}