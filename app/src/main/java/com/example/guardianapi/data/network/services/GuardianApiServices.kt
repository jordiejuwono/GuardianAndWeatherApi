package com.example.guardianapi.data.network.services

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.guardianapi.BuildConfig
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface GuardianApiServices {

    //get recent news
    @GET("search?page-size=20&show-fields=thumbnail")
    suspend fun getArticleList(
        @Query("page") currentPage: Int = 1,
        @Query("api-key") apiKey: String = BuildConfig.API_KEY_GUARDIAN
    ) : GuardianApiResponse

    //search for news
    @GET("search?page-size=20&show-fields=thumbnail")
    suspend fun getSearchedArticleList(
        @Query("q") search: String?,
        @Query("page") currentPage: Int = 1,
        @Query("api-key") apiKey: String = BuildConfig.API_KEY_GUARDIAN
    ) : GuardianApiResponse

    //get news by section
    @GET("search?page-size=20&show-fields=thumbnail")
    suspend fun getArticleListBySection(
        @Query("page") currentPage: Int = 1,
        @Query("section") section: String,
        @Query("api-key") apiKey: String = BuildConfig.API_KEY_GUARDIAN
    ) : GuardianApiResponse

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor) : GuardianApiServices {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.GUARDIAN_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GuardianApiServices::class.java)
        }
    }

}