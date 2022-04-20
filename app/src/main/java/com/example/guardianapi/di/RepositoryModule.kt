package com.example.guardianapi.di

import com.example.guardianapi.data.local.room.datasource.ArticlesDataSource
import com.example.guardianapi.data.network.datasource.guardianapi.GuardianApiDataSource
import com.example.guardianapi.data.network.datasource.weatherapi.WeatherApiDataSource
import com.example.guardianapi.ui.article.articlebysection.ArticleListSectionRepository
import com.example.guardianapi.ui.article.articlelist.ArticleListRepository
import com.example.guardianapi.ui.article.savedarticles.SavedArticlesRepository
import com.example.guardianapi.ui.article.searcharticle.SearchArticleRepository
import com.example.guardianapi.ui.mainfragment.MainFragmentRepository
import com.example.guardianapi.ui.weather.WeatherRepository
import com.example.guardianapi.ui.webview.WebViewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideArticleRepository(articleDataSource: GuardianApiDataSource): ArticleListRepository {
        return ArticleListRepository(articleDataSource)
    }

    @Singleton
    @Provides
    fun provideSearchedArticleRepository(articleDataSource: GuardianApiDataSource): SearchArticleRepository {
        return SearchArticleRepository(articleDataSource)
    }

    @Singleton
    @Provides
    fun provideArticleListSectionRepository(articleDataSource: GuardianApiDataSource): ArticleListSectionRepository {
        return ArticleListSectionRepository(articleDataSource)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherApiDataSource: WeatherApiDataSource): WeatherRepository {
        return WeatherRepository(weatherApiDataSource)
    }

    @Singleton
    @Provides
    fun provideCategoriesRepository(articleDataSource: GuardianApiDataSource): MainFragmentRepository {
        return MainFragmentRepository(articleDataSource)
    }

    @Singleton
    @Provides
    fun providesWebViewRepository(savedArticlesDataSource: ArticlesDataSource): WebViewRepository {
        return WebViewRepository(savedArticlesDataSource)
    }

    @Singleton
    @Provides
    fun providesSavedArticlesListRepository(savedArticlesDataSource: ArticlesDataSource): SavedArticlesRepository {
        return SavedArticlesRepository(savedArticlesDataSource)
    }
}