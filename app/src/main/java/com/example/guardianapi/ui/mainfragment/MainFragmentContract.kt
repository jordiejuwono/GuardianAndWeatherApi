package com.example.guardianapi.ui.mainfragment

import androidx.lifecycle.LiveData
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import com.example.guardianapi.data.network.model.guardianapi.Result

interface MainFragmentContract {
    interface View {
        fun getData()
        fun initList()
        fun setDataAdapter(data: List<Result>)
        fun setOnRefreshListener()
        fun setOnClickListenerCategories()
    }
    interface Repository {
        suspend fun getArticleList(currentPage: Int) : GuardianApiResponse
    }
    interface ViewModel {
        fun getArticleList(currentPage: Int)
        fun getArticleListLiveData() : LiveData<Resource<GuardianApiResponse>>
    }
}