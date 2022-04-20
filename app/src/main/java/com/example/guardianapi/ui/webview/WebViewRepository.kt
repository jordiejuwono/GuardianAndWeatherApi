package com.example.guardianapi.ui.webview

import com.example.guardianapi.base.arch.BaseRepositoryImpl
import com.example.guardianapi.data.local.room.datasource.ArticlesDataSource
import com.example.guardianapi.data.local.room.entity.Articles
import javax.inject.Inject

class WebViewRepository
    @Inject constructor(private val articlesDataSource: ArticlesDataSource): BaseRepositoryImpl(), WebViewContract.Repository {
    override suspend fun saveArticle(saveArticle: Articles): Long {
        return articlesDataSource.saveArticle(saveArticle)
    }
}