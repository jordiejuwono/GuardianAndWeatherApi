package com.example.guardianapi.ui.article.savedarticles

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.guardianapi.base.arch.BaseViewModelImpl
import com.example.guardianapi.data.local.room.entity.Articles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedArticlesViewModel
@Inject constructor(private val repository: SavedArticlesRepository) : BaseViewModelImpl(),
    SavedArticlesContract.ViewModel {

    override fun showSavedArticlesList(): LiveData<List<Articles>> {
        return repository.showSavedArticlesList()
    }

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