package com.example.guardianapi.data.network.model.weatherapi.onecall


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    var h: Double?
)