package com.example.guardianapi.data.network.model.guardianapi


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    @SerializedName("id")
    var id: String?,
    @SerializedName("webTitle")
    var webTitle: String?,
    @SerializedName("webUrl")
    var webUrl: String?,
    @SerializedName("webPublicationDate")
    var articleDate: String?,
    @SerializedName("sectionId")
    var sectionId: String?,
    @SerializedName("sectionName")
    var sectionName: String?,
    @SerializedName("fields")
    var fields: Fields?
) : Parcelable