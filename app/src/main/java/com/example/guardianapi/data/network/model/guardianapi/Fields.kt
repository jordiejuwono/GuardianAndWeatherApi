package com.example.guardianapi.data.network.model.guardianapi


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fields(
    @SerializedName("thumbnail")
    var thumbnail: String?
) : Parcelable