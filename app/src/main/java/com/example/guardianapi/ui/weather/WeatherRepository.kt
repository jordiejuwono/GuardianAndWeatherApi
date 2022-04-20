package com.example.guardianapi.ui.weather

import com.example.guardianapi.base.arch.BaseRepositoryImpl
import com.example.guardianapi.data.network.datasource.weatherapi.WeatherApiDataSource
import com.example.guardianapi.data.network.model.weatherapi.onecall.WeatherOneCallResponse
import javax.inject.Inject

class WeatherRepository
    @Inject constructor(private val weatherApiDataSource: WeatherApiDataSource): BaseRepositoryImpl(), WeatherContract.Repository {

    override suspend fun getWeatherOneCallData(): WeatherOneCallResponse {
        return weatherApiDataSource.getWeatherOneCallData()
    }
}