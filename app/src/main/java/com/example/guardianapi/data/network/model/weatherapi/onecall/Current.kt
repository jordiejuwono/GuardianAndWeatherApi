package com.example.guardianapi.data.network.model.weatherapi.onecall

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("dt")
    var dt: Int?,
    @SerializedName("feels_like")
    var feelsLike: Double?,
    @SerializedName("humidity")
    var humidity: Int?,
    @SerializedName("pressure")
    var pressure: Int?,
    @SerializedName("temp")
    var temp: Double?,
    @SerializedName("weather")
    var weather: List<Weather>?,
    @SerializedName("wind_deg")
    var windDeg: Int?,
    @SerializedName("wind_speed")
    var windSpeed: Double?
)