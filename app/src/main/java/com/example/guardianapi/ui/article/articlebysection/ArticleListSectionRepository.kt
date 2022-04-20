package com.example.guardianapi.ui.article.articlebysection

import com.example.guardianapi.base.arch.BaseRepositoryImpl
import com.example.guardianapi.data.network.datasource.guardianapi.GuardianApiDataSource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import javax.inject.Inject

class ArticleListSectionRepository
@Inject constructor(
    private val dataSource: GuardianApiDataSource
): ArticleListSectionContract.Repository, BaseRepositoryImpl() {
    override suspend fun getArticleListBySection(currentPage: Int, section: String): GuardianApiResponse {
        return dataSource.getArticleListBySection(currentPage, section)
    }

}