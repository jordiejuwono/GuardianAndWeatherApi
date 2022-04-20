package com.example.guardianapi.data.network.model.guardianapi


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("currentPage")
    var currentPage: Int?,
    @SerializedName("orderBy")
    var orderBy: String?,
    @SerializedName("pageSize")
    var pageSize: Int?,
    @SerializedName("pages")
    var pages: Int?,
    @SerializedName("results")
    var results: List<Result>?,
    @SerializedName("startIndex")
    var startIndex: Int?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("total")
    var total: Int?,
    @SerializedName("userTier")
    var userTier: String?
)