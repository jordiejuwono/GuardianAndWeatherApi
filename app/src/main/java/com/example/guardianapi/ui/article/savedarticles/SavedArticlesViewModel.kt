package com.example.guardianapi.ui.article.savedarticles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guardianapi.base.arch.BaseViewModelImpl
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.local.room.entity.Articles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SavedArticlesViewModel
@Inject constructor(private val repository: SavedArticlesRepository) : BaseViewModelImpl(),
    SavedArticlesContract.ViewModel {

//    private val savedArticleListResponse = MutableLiveData<Resource<List<Articles>>>()

    override fun showSavedArticlesList(): LiveData<List<Articles>> {
        return repository.showSavedArticlesList()
    }

//    override fun showSavedArticlesListLiveData(): LiveData<Resource<List<Articles>>> =
//        savedArticleListResponse
//
//    override fun showSavedArticlesList() {
//        savedArticleListResponse.value = Resource.Loading()
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = repository.showSavedArticlesList()
//                viewModelScope.launch(Dispatchers.Main) {
//                    savedArticleListResponse.value = Resource.Success(response)
//                }
//            } catch (e: Exception) {
//                savedArticleListResponse.postValue(Resource.Error(e.message.orEmpty()))
//            }
//        }
//    }


    override fun deleteSavedArticle(article: Articles) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSavedArticle(article)
        }
    }

    override fun undoArticleDelete(article: Articles) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.undoArticleDelete(article)
        }
    }
}