package com.example.guardianapi.data.local.room.datasource

import androidx.lifecycle.LiveData
import com.example.guardianapi.data.local.room.dao.ArticlesDao
import com.example.guardianapi.data.local.room.entity.Articles
import javax.inject.Inject

class ArticlesDataSourceImpl
    @Inject constructor(private val dao: ArticlesDao) : ArticlesDataSource {
    override suspend fun saveArticle(article: Articles): Long {
        return dao.saveArticle(article)
    }

    override suspend fun deleteArticle(article: Articles): Int {
        return dao.deleteArticle(article)
    }

    override fun getAllSavedArticles(): LiveData<List<Articles>> {
        return dao.getAllSavedArticles()
    }
}