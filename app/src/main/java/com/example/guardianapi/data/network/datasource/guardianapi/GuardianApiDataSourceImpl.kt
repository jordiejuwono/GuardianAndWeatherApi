package com.example.guardianapi.data.network.datasource.guardianapi

import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import com.example.guardianapi.data.network.services.GuardianApiServices
import javax.inject.Inject

class GuardianApiDataSourceImpl
@Inject constructor(private val guardianApiServices: GuardianApiServices) : GuardianApiDataSource {
    override suspend fun getArticleList(currentPage: Int): GuardianApiResponse {
        return guardianApiServices.getArticleList(currentPage)
    }

    override suspend fun getSearchedArticleList(querySearch: String, currentPage: Int): GuardianApiResponse {
        return guardianApiServices.getSearchedArticleList(querySearch, currentPage)
    }

    override suspend fun getArticleListBySection(
        currentPage: Int,
        section: String
    ): GuardianApiResponse {
        return guardianApiServices.getArticleListBySection(currentPage, section)
    }

}