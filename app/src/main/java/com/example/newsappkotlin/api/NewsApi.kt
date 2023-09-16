package com.example.newsappkotlin.api

import com.example.newsappkotlin.models.newsArticles
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
     ) : Response<newsArticles>
      @GET("/v2/everything")
   suspend  fun searchNews(
        @Query("q")
        country:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = API_KEY
     ) : Response<newsArticles>


}
//object NewsServise{
//    val newsInstance: newsApi
//    init {
//        val retrofit = Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build()
//        newsInstance= retrofit.create(newsApi::class.java)
//
//    }
//}