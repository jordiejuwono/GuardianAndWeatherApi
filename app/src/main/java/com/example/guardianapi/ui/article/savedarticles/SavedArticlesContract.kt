package com.example.guardianapi.ui.article.savedarticles

import androidx.lifecycle.LiveData
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.local.room.entity.Articles

interface SavedArticlesContract {
    interface View {
        fun initList()
        fun getData()
        fun setDataAdapter(data: List<Articles>?)
    }
    interface Repository {
        fun showSavedArticlesList() : LiveData<List<Articles>>
        suspend fun deleteSavedArticle(article: Articles) : Int
        suspend fun undoArticleDelete(article: Articles) : Long
    }
    interface ViewModel {
        fun showSavedArticlesList() : LiveData<List<Articles>>
        fun deleteSavedArticle(article: Articles)
        fun undoArticleDelete(article: Articles)
    }
}