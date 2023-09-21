package com.example.newsappkotlin.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappkotlin.models.Article
import com.example.newsappkotlin.models.NewsArticles
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.util.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NewsRepository) : ViewModel() {


//    init {
//        getNews("us",1)
//    }
    val articleLiveData : LiveData<Resource<NewsArticles>>
        get() = repository.articleLiveData
    val searchLiveData : LiveData<Resource<NewsArticles>>
        get() = repository.searchLiveData

    fun getNews(country : String, page : Int){
        viewModelScope.launch {
            repository.getNews(country, page)
        }
    }
    fun searchNews(query : String, page : Int) {
        viewModelScope.launch {
            repository.searchNews(query,page)
        }
    }

    fun saveArticles(article: Article) = viewModelScope.launch {
        repository.saveArticle(article)
    }

    fun deleteSavedArticle(article: Article) = viewModelScope.launch {
        repository.deleteSavedArticle(article)
    }
    fun getSavedArticles()  = repository.getSavedArticle()

}

