package com.example.newsapikotlin.api

import com.example.newsapikotlin.model.ArticlesResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ArticlesAPIInterface {

    @Headers("X-Api-Key: e8b5f8befe1d4a3b848614cb34d5522d")
    @GET("v2/top-headlines?country=us")
    fun getArticles(
        @Query("category") category:String
    ):Call<ArticlesResult>
}