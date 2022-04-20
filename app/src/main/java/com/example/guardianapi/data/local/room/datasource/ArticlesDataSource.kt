package com.example.guardianapi.data.local.room.datasource

import androidx.lifecycle.LiveData
import com.example.guardianapi.data.local.room.entity.Articles

interface ArticlesDataSource {

    suspend fun saveArticle(article: Articles): Long

    suspend fun deleteArticle(article: Articles): Int

    fun getAllSavedArticles(): LiveData<List<Articles>>

}