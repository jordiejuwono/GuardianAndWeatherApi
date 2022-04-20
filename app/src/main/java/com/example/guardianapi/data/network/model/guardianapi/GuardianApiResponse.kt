package com.example.guardianapi.data.network.model.guardianapi


import com.google.gson.annotations.SerializedName

data class GuardianApiResponse(
    @SerializedName("response")
    var response: Response?
)