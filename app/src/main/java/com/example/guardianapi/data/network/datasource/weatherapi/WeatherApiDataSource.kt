package com.example.guardianapi.data.network.datasource.weatherapi

import com.example.guardianapi.data.network.model.weatherapi.onecall.WeatherOneCallResponse

interface WeatherApiDataSource {
    suspend fun getWeatherOneCallData() : WeatherOneCallResponse
}