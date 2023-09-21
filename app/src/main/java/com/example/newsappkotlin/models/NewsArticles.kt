package com.example.newsappkotlin.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticles(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
):Parcelable