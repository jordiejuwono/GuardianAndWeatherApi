package com.example.guardianapi.di

import android.content.Context
import androidx.room.Room
import com.example.guardianapi.data.local.room.database.ArticlesDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context)
    = Room.databaseBuilder(context, ArticlesDatabase::class.java, "articles.db").build()

    @Singleton
    @Provides
    fun provideDao(database: ArticlesDatabase)
    = database.articlesDao()

    @Singleton
    @Provides
    fun provideGson() : Gson {
        return Gson()
    }
}