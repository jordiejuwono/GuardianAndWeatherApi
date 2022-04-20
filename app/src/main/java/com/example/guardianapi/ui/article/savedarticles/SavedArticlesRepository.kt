package com.example.guardianapi.ui.article.savedarticles

import androidx.lifecycle.LiveData
import com.example.guardianapi.base.arch.BaseRepositoryImpl
import com.example.guardianapi.data.local.room.datasource.ArticlesDataSource
import com.example.guardianapi.data.local.room.entity.Articles
import javax.inject.Inject

class SavedArticlesRepository
    @Inject constructor(private val articleDataSource: ArticlesDataSource): BaseRepositoryImpl(), SavedArticlesContract.Repository {
    override fun showSavedArticlesList(): LiveData<List<Articles>> {
        return articleDataSource.getAllSavedArticles()
    }

    override suspend fun deleteSavedArticle(article: Articles): Int {
        return articleDataSource.deleteArticle(article)
    }

    override suspend fun undoArticleDelete(article: Articles): Long {
        return articleDataSource.saveArticle(article)
    }
}