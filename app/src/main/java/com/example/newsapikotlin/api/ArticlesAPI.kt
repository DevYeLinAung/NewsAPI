package com.example.newsapikotlin.api

import com.example.newsapikotlin.model.ArticlesResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticlesAPI {

    private val articlesAPIInterface:ArticlesAPIInterface

    companion object{
        const val BASE_URL = "https://newsapi.org/"
    }

    init {
        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) ///later
            .build()    ///later

        articlesAPIInterface = retrofit.create(ArticlesAPIInterface::class.java)
    }

    fun getResult():Call<ArticlesResult>{
        return articlesAPIInterface.getArticles("business")
    }
}