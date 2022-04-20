package com.example.guardianapi.di

import com.example.guardianapi.base.arch.ViewModelFactory
import com.example.guardianapi.ui.article.savedarticles.SavedArticlesRepository
import com.example.guardianapi.ui.article.savedarticles.SavedArticlesViewModel
import com.example.guardianapi.ui.mainfragment.MainFragmentRepository
import com.example.guardianapi.ui.mainfragment.MainFragmentViewModel
import com.example.guardianapi.ui.weather.WeatherRepository
import com.example.guardianapi.ui.weather.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object ViewModelFragmentModule {

    @Provides
    @FragmentScoped
    fun provideWeatherViewModel(
        weatherApiRepository: WeatherRepository
    ) : WeatherViewModel {
        return ViewModelFactory(WeatherViewModel(weatherApiRepository)).create(
            WeatherViewModel::class.java
        )
    }

    @Provides
    @FragmentScoped
    fun provideCategoriesViewModel(
        mainFragmentRepository: MainFragmentRepository
    ) : MainFragmentViewModel {
        return ViewModelFactory(MainFragmentViewModel(mainFragmentRepository)).create(
            MainFragmentViewModel::class.java
        )
    }

    @Provides
    @FragmentScoped
    fun provideSavedArticlesViewModel(
        savedArticlesRepository: SavedArticlesRepository
    ) : SavedArticlesViewModel {
        return ViewModelFactory(SavedArticlesViewModel(savedArticlesRepository)).create(
            SavedArticlesViewModel::class.java
        )
    }
}