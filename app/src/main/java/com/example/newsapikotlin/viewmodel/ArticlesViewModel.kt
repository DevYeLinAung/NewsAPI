package com.example.newsapikotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapikotlin.api.ArticlesAPI
import com.example.newsapikotlin.model.ArticlesResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesViewModel:ViewModel(){

    private val articlesApi:ArticlesAPI = ArticlesAPI()
    var results:MutableLiveData<ArticlesResult> = MutableLiveData()
    var articlesLoadError:MutableLiveData<Boolean> = MutableLiveData()
    var loading:MutableLiveData<Boolean> = MutableLiveData()

    fun getResults(): LiveData<ArticlesResult> = results

    fun getError():LiveData<Boolean> = articlesLoadError

    fun getLoading(): LiveData<Boolean> = loading

    fun loadResults(){

        loading.value = true
//        val call = articlesApi.getResult()
        //call api
        articlesApi.getResult().enqueue(object : Callback<ArticlesResult>{
            override fun onFailure(call: Call<ArticlesResult>, t: Throwable) {
                loading.value = false
                articlesLoadError.value = true
            }

            override fun onResponse(
                call: Call<ArticlesResult>,
                response: Response<ArticlesResult>
            ) {
                response.isSuccessful.let {
                    loading.value = false
                    var resultList = ArticlesResult(
                        response?.body()?.articles?: emptyList())
                    results.value = resultList
                }
            }

        })
    }
}