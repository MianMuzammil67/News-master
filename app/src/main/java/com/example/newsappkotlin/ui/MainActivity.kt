package com.example.newsappkotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.newsappkotlin.R
import com.example.newsappkotlin.api.NewsApi
import com.example.newsappkotlin.api.RetrofitHelper
import com.example.newsappkotlin.databinding.ActivityMainBinding
import com.example.newsappkotlin.db.ArticleDatabase
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.ui.viewModel.MainViewModel
import com.example.newsappkotlin.ui.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
     private lateinit var binding: ActivityMainBinding
      lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val apiService = RetrofitHelper.retrofitInstance().create(NewsApi::class.java)
        val repository = NewsRepository(ArticleDatabase.getDatabaseInstance(this),apiService)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        val navConroller = findNavController(R.id.newsNavHostFragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navConroller)


    }

//    private fun getNews() {
//        val news= NewsServise.newsInstance.getNews("us",1)
//        news.enqueue(object : Callback<newsArticles>{
//            override fun onResponse(call: Call<newsArticles>, response: Response<newsArticles>) {
//                val news = response.body()
//                if (news != null){
//                    Toast.makeText(this@MainActivity, response.toString(), Toast.LENGTH_LONG).show()
//                    Log.d("mainActivity",news.toString())
//
//                }
//            }
//
//            override fun onFailure(call: Call<newsArticles>, t: Throwable) {
//                Log.d("mainActivity","somthing went wrong",t)
//            }
//        })
//    }

}