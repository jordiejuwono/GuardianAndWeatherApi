package com.example.guardianapi.data.network.model.weatherapi.onecall

import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("dt")
    var dt: Int?,
    @SerializedName("temp")
    var temp: Double?,
    @SerializedName("weather")
    var weather: List<WeatherXX>?
)