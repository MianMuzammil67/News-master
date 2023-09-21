package com.example.newsappkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappkotlin.models.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
    companion object {
        private var Instance: ArticleDatabase? = null
        fun getDatabaseInstance(context: Context): ArticleDatabase {
            if (Instance == null) {
                synchronized(this) {
                    Instance = Room.databaseBuilder(
                        context,
                        ArticleDatabase::class.java,
                        "Article_db"
                    )
                        .build()
                }
            }
            return Instance!!
        }


    }

}