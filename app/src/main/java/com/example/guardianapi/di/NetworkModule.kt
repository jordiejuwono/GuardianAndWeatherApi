package com.example.guardianapi.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.guardianapi.data.network.services.GuardianApiServices
import com.example.guardianapi.data.network.services.OpenWeatherApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideGuardianApiServices(chuckerInterceptor: ChuckerInterceptor): GuardianApiServices {
        return GuardianApiServices.invoke(chuckerInterceptor)
    }

    @Singleton
    @Provides
    fun provideWeatherApiServices(chuckerInterceptor: ChuckerInterceptor): OpenWeatherApiServices {
        return OpenWeatherApiServices.invoke(chuckerInterceptor)
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }
}