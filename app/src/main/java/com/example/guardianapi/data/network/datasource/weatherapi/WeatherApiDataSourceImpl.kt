package com.example.guardianapi.data.network.datasource.weatherapi

import com.example.guardianapi.data.network.model.weatherapi.onecall.WeatherOneCallResponse
import com.example.guardianapi.data.network.services.OpenWeatherApiServices
import javax.inject.Inject

class WeatherApiDataSourceImpl
@Inject constructor(private val openWeatherApiServices: OpenWeatherApiServices) : WeatherApiDataSource{

    override suspend fun getWeatherOneCallData(): WeatherOneCallResponse {
        return openWeatherApiServices.getWeatherOneCallData()
    }

}