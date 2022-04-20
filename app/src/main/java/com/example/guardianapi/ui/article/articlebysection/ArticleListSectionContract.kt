package com.example.guardianapi.ui.article.articlebysection

import androidx.lifecycle.LiveData
import com.example.guardianapi.base.arch.BaseContract
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import com.example.guardianapi.data.network.model.guardianapi.Result

class ArticleListSectionContract {
    interface View : BaseContract.View {
        fun getData()
        fun initList()
        fun initSwipeRefresh()
        fun setDataAdapter(data: List<Result>)
        fun addPagination()
    }
    interface Repository : BaseContract.Repository {
        suspend fun getArticleListBySection(currentPage: Int, section: String) : GuardianApiResponse
    }
    interface ViewModel : BaseContract.ViewModel {
        fun getArticleListBySection(currentPage: Int, section: String)
        fun getArticleListSectionLiveData() : LiveData<Resource<GuardianApiResponse>>
    }
}