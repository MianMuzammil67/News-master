package com.example.newsappkotlin.api

import com.example.newsappkotlin.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {


    fun retrofitInstance():Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build()
    }

}