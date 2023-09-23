package com.example.newsappkotlin.di

import android.content.Context
import androidx.room.Room
import com.example.newsappkotlin.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) : ArticleDatabase{
        return Room.databaseBuilder(context,ArticleDatabase::class.java,"Article_db").build()
    }

}