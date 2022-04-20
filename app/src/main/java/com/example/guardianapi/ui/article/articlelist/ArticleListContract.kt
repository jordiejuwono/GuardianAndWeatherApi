package com.example.guardianapi.ui.article.articlelist

import androidx.lifecycle.LiveData
import com.example.guardianapi.base.arch.BaseContract
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import com.example.guardianapi.data.network.model.guardianapi.Result

interface ArticleListContract {
    interface View : BaseContract.View {
        fun getData()
        fun initList()
        fun initSwipeRefresh()
        fun setDataAdapter(data: List<Result>)
        fun addPagination()
    }
    interface Repository : BaseContract.Repository {
        suspend fun getArticleList(currentPage: Int) : GuardianApiResponse
    }
    interface ViewModel : BaseContract.ViewModel {
        fun getArticleList(currentPage: Int)
        fun getArticleListLiveData() : LiveData<Resource<GuardianApiResponse>>
    }
}