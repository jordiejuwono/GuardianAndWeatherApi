package com.example.guardianapi.data.network.services

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.guardianapi.BuildConfig
import com.example.guardianapi.data.network.model.weatherapi.onecall.WeatherOneCallResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface OpenWeatherApiServices {

    @GET("onecall?lat=-6.2&lon=106.81666&exclude=minutely&appid=${BuildConfig.API_KEY_WEATHER}")
    suspend fun getWeatherOneCallData() : WeatherOneCallResponse


    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor) : OpenWeatherApiServices {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.OPEN_WEATHER_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(OpenWeatherApiServices::class.java)
        }
    }

}