package com.example.newsappkotlin.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsappkotlin.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun UpsertRdbArticles(articles: Article)

    @Query("Select * From Article_db")
    fun getRdbArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteRdbArticles(articles: Article)

}