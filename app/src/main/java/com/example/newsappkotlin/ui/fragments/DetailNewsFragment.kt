package com.example.newsappkotlin.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsappkotlin.R
import com.example.newsappkotlin.api.NewsApi
import com.example.newsappkotlin.api.RetrofitHelper
import com.example.newsappkotlin.databinding.DetailFragmentBinding
import com.example.newsappkotlin.db.ArticleDatabase
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.ui.viewModel.MainViewModel
import com.example.newsappkotlin.ui.viewModel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class DetailNewsFragment : Fragment(R.layout.detail_fragment) {
    private lateinit var binding: DetailFragmentBinding

   private val arg: DetailNewsFragmentArgs by navArgs()

    private lateinit var viewModel: MainViewModel
    private val TAG = "DetailFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailFragmentBinding.bind(view)
//        val myModel = arguments?.getSerializable("article") as Article

        val apiService = RetrofitHelper.retrofitInstance().create(NewsApi::class.java)
        val repository =
            NewsRepository(ArticleDatabase.getDatabaseInstance(requireContext()), apiService)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]


        Toast.makeText(requireContext(), "detail: ${arg.article.url})", Toast.LENGTH_LONG).show()

        binding.webView.apply {
            webViewClient = WebViewClient()

            settings.javaScriptEnabled
            settings.javaScriptCanOpenWindowsAutomatically
            settings.loadsImagesAutomatically
            settings.cacheMode = WebSettings.LOAD_NO_CACHE;
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            loadUrl(arg.article.url)
//            loadUrl(myModel.url)

        }
        binding.floatingActionButton.setOnClickListener {
            arg.article.let {article->
                viewModel.saveArticles(article)
            }
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }


    }
}