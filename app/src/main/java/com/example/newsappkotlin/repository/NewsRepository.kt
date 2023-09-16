package com.example.newsappkotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsappkotlin.api.NewsApi
import com.example.newsappkotlin.db.ArticleDatabase
import com.example.newsappkotlin.models.newsArticles
import com.example.newsappkotlin.util.Resource
import org.json.JSONObject

class NewsRepository(val db : ArticleDatabase, private val api: NewsApi) {

    private val _articleLiveData = MutableLiveData<Resource<newsArticles>>()

    val articleLiveData : LiveData<Resource<newsArticles>>
        get() = _articleLiveData
    suspend fun getNews(country : String, page: Int){
        _articleLiveData.postValue(Resource.Loading())
        val result = api.getNews(country,page)
        if (result.body() != null){
            _articleLiveData.postValue(Resource.Success(result.body()))
        }else{
            val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
            _articleLiveData.postValue(Resource.Error(errorObj.getString("Message")))
        }

    }
}