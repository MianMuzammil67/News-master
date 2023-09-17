package com.example.newsappkotlin.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsappkotlin.R
import com.example.newsappkotlin.databinding.DetailFragmentBinding
import com.example.newsappkotlin.ui.MainActivity
import com.example.newsappkotlin.ui.viewModel.MainViewModel

class DetailNewsFragment: Fragment(R.layout.detail_fragment) {
    private lateinit var  binding: DetailFragmentBinding
    private val arg : DetailNewsFragmentArgs by navArgs()
    lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailFragmentBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

//        val apiService = RetrofitHelper.retrofitInstance().create(NewsApi::class.java)
//        val repository =
//            NewsRepository(ArticleDatabase.getDatabaseInstance(requireContext()), apiService)
//        val factory = ViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        val news = arg.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(news.url)

        }






    }
}