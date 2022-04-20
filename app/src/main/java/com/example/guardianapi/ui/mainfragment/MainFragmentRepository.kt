package com.example.guardianapi.ui.mainfragment

import com.example.guardianapi.base.arch.BaseRepositoryImpl
import com.example.guardianapi.data.network.datasource.guardianapi.GuardianApiDataSource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import javax.inject.Inject

class MainFragmentRepository
    @Inject constructor(private val articleDataSource: GuardianApiDataSource): BaseRepositoryImpl(), MainFragmentContract.Repository {
    override suspend fun getArticleList(currentPage: Int): GuardianApiResponse {
        return articleDataSource.getArticleList(currentPage)
    }
}