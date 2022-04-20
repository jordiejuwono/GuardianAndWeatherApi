package com.example.guardianapi.di

import com.example.guardianapi.data.local.room.dao.ArticlesDao
import com.example.guardianapi.data.local.room.datasource.ArticlesDataSource
import com.example.guardianapi.data.local.room.datasource.ArticlesDataSourceImpl
import com.example.guardianapi.data.network.datasource.guardianapi.GuardianApiDataSource
import com.example.guardianapi.data.network.datasource.guardianapi.GuardianApiDataSourceImpl
import com.example.guardianapi.data.network.datasource.weatherapi.WeatherApiDataSource
import com.example.guardianapi.data.network.datasource.weatherapi.WeatherApiDataSourceImpl
import com.example.guardianapi.data.network.services.GuardianApiServices
import com.example.guardianapi.data.network.services.OpenWeatherApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideArticleDataSource(articleApiServices: GuardianApiServices) : GuardianApiDataSource {
        return GuardianApiDataSourceImpl(articleApiServices)
    }

    @Singleton
    @Provides
    fun provideWeatherDataSource(weatherApiServices: OpenWeatherApiServices) : WeatherApiDataSource {
        return WeatherApiDataSourceImpl(weatherApiServices)
    }

    @Singleton
    @Provides
    fun provideArticlesDataSource(articlesDao: ArticlesDao): ArticlesDataSource {
        return ArticlesDataSourceImpl(articlesDao)
    }
}