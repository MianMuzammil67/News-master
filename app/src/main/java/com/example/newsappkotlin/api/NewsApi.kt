package com.example.newsappkotlin.api

import com.example.newsappkotlin.models.NewsArticles
import com.example.newsappkotlin.util.Constants.Companion.API_KEY
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        country:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = API_KEY
     ) : Response<NewsArticles>
      @GET("/v2/everything")
   suspend  fun searchNews(
        @Query("q")
        country:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = API_KEY
     ) : Response<NewsArticles>




}
