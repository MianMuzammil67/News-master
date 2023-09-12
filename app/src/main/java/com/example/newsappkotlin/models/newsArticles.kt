package com.example.newsappkotlin.models


data class newsArticles(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)