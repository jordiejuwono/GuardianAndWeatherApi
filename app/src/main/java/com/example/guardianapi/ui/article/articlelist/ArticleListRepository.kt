package com.example.guardianapi.ui.article.articlelist

import com.example.guardianapi.base.arch.BaseRepositoryImpl
import com.example.guardianapi.data.network.datasource.guardianapi.GuardianApiDataSource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import javax.inject.Inject

class ArticleListRepository
    @Inject constructor(
        private val dataSource: GuardianApiDataSource
    ): ArticleListContract.Repository, BaseRepositoryImpl() {
    override suspend fun getArticleList(currentPage: Int): GuardianApiResponse {
        return dataSource.getArticleList(currentPage)
    }
}