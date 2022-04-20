package com.example.guardianapi.ui.weather

import androidx.lifecycle.LiveData
import com.example.guardianapi.base.arch.BaseContract
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.weatherapi.onecall.Daily
import com.example.guardianapi.data.network.model.weatherapi.onecall.Hourly
import com.example.guardianapi.data.network.model.weatherapi.onecall.WeatherOneCallResponse

interface WeatherContract {
    interface View : BaseContract.View {
        fun getData()
        fun setOnRefreshListener()
        fun setDataToView(response: WeatherOneCallResponse)
        fun initListWeather()
        fun setDataAdapter(data: List<Hourly>)
        fun setDataAdapterForDaily(data: List<Daily>)
    }

    interface Repository : BaseContract.Repository {
        suspend fun getWeatherOneCallData() : WeatherOneCallResponse
    }

    interface ViewModel : BaseContract.ViewModel {
        fun getWeatherOneCallData()
        fun getWeatherOneCallLiveData() : LiveData<Resource<WeatherOneCallResponse>>
    }
}