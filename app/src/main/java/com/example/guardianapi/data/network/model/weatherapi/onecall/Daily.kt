package com.example.guardianapi.data.network.model.weatherapi.onecall

import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("dt")
    var dt: Int?,
    @SerializedName("feels_like")
    var feelsLike: FeelsLike?,
    @SerializedName("humidity")
    var humidity: Int?,
    @SerializedName("pressure")
    var pressure: Int?,
    @SerializedName("temp")
    var temp: Temp?,
    @SerializedName("weather")
    var weather: List<WeatherX>?,
    @SerializedName("wind_deg")
    var windDeg: Int?,
    @SerializedName("wind_gust")
    var windGust: Double?,
    @SerializedName("wind_speed")
    var windSpeed: Double?
)