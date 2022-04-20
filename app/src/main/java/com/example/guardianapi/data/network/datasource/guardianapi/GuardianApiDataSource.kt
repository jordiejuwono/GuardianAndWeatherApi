package com.example.guardianapi.data.network.datasource.guardianapi

import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse

interface GuardianApiDataSource {
    suspend fun getArticleList(currentPage: Int) : GuardianApiResponse
    suspend fun getSearchedArticleList(querySearch: String, currentPage: Int) : GuardianApiResponse
    suspend fun getArticleListBySection(currentPage: Int, section: String) : GuardianApiResponse
}