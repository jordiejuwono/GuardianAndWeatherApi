package com.example.guardianapi.ui.article.articlelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guardianapi.base.arch.BaseViewModelImpl
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel
    @Inject constructor(private val repository: ArticleListRepository): ArticleListContract.ViewModel, BaseViewModelImpl() {

    private val articleListResponseLiveData = MutableLiveData<Resource<GuardianApiResponse>>()

    override fun getArticleList(currentPage: Int) {
        articleListResponseLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getArticleList(currentPage)
                viewModelScope.launch(Dispatchers.Main) {
                    articleListResponseLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    articleListResponseLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun getArticleListLiveData(): LiveData<Resource<GuardianApiResponse>> = articleListResponseLiveData
}