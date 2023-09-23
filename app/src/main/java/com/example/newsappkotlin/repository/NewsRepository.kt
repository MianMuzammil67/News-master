package com.example.newsappkotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsappkotlin.api.NewsApi
import com.example.newsappkotlin.db.ArticleDatabase
import com.example.newsappkotlin.models.Article
import com.example.newsappkotlin.models.NewsArticles
import com.example.newsappkotlin.util.Resource
import org.json.JSONObject
import javax.inject.Inject

class NewsRepository @Inject constructor(val db: ArticleDatabase, private val api: NewsApi) {

    private val _articleLiveData = MutableLiveData<Resource<NewsArticles>>()
    private val _searchLiveData = MutableLiveData<Resource<NewsArticles>>()

    val articleLiveData: LiveData<Resource<NewsArticles>>
        get() = _articleLiveData
    val searchLiveData: LiveData<Resource<NewsArticles>>
        get() = _searchLiveData

    suspend fun getNews(country: String, page: Int) {
        _articleLiveData.postValue(Resource.Loading())
        val result = api.getNews(country, page)
        if (result.body() != null) {
            _articleLiveData.postValue(Resource.Success(result.body()))
        } else {
            val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
            _articleLiveData.postValue(Resource.Error(errorObj.getString("Message")))
        }
    }

    suspend fun searchNews(query: String, page: Int) {
        _searchLiveData.postValue(Resource.Loading())
        val result = api.searchNews(query, page)
        if (result.body() != null) {
            _searchLiveData.postValue(Resource.Success(result.body()))
        } else {
            val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
            _searchLiveData.postValue(Resource.Error(errorObj.getString("Messagee")))
        }

    }

    suspend fun saveArticle(article : Article)=db.getArticleDao().UpsertRdbArticles(article)
    suspend fun deleteSavedArticle(article: Article)= db.getArticleDao().deleteRdbArticles(article)
    fun getSavedArticle()=db.getArticleDao().getRdbArticles()
}