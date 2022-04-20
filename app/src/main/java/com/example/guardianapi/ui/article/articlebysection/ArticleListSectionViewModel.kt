package com.example.guardianapi.ui.article.articlebysection

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
class ArticleListSectionViewModel
@Inject constructor(private val repository: ArticleListSectionRepository): ArticleListSectionContract.ViewModel, BaseViewModelImpl() {

    private val articleListSectionResponse = MutableLiveData<Resource<GuardianApiResponse>>()

    override fun getArticleListBySection(currentPage: Int, section: String) {
//        articleListResponseLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getArticleListBySection(currentPage, section)
                viewModelScope.launch(Dispatchers.Main) {
                    articleListSectionResponse.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    articleListSectionResponse.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun getArticleListSectionLiveData(): LiveData<Resource<GuardianApiResponse>> = articleListSectionResponse
}