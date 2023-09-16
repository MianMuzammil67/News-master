package com.example.newsappkotlin.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappkotlin.models.newsArticles
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.util.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NewsRepository) : ViewModel() {


//    init {
//        getNews("us",1)
//    }
    val articleLiveData : LiveData<Resource<newsArticles>>
        get() = repository.articleLiveData

    fun getNews(country : String, page : Int){
        viewModelScope.launch {
            repository.getNews(country, page)
        }
    }




}

