package com.example.guardianapi.ui.article.searcharticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guardianapi.base.arch.BaseViewModelImpl
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchArticleViewModel
    @Inject constructor(private val repository: SearchArticleRepository): BaseViewModelImpl(), SearchArticleContract.ViewModel {

    private val searchedArticleResponseLiveData = MutableLiveData<Resource<GuardianApiResponse>>()

    override fun getSearchedArticleList(querySearch: String, currentPage: Int) {
//        searchedArticleResponseLiveData.value = Resource.Loading()
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response = repository.getSearchedArticleList(querySearch, currentPage)
                viewModelScope.launch(Dispatchers.Main) {
                    searchedArticleResponseLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    searchedArticleResponseLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }

    }

    override fun getSearchedArticleListLiveData(): LiveData<Resource<GuardianApiResponse>> = searchedArticleResponseLiveData
}