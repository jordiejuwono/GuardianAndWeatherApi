package com.example.guardianapi.ui.article.searcharticle

import com.example.guardianapi.base.arch.BaseRepositoryImpl
import com.example.guardianapi.data.network.datasource.guardianapi.GuardianApiDataSource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import javax.inject.Inject

class SearchArticleRepository
    @Inject constructor(
        private val dataSource: GuardianApiDataSource
    ): BaseRepositoryImpl(), SearchArticleContract.Repository {
    override suspend fun getSearchedArticleList(querySearch: String, currentPage: Int): GuardianApiResponse {
        return dataSource.getSearchedArticleList(querySearch, currentPage)
    }
}