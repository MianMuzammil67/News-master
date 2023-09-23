package com.example.newsappkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsappkotlin.R
import com.example.newsappkotlin.databinding.DetailFragmentBinding
import com.example.newsappkotlin.ui.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailNewsFragment : Fragment(R.layout.detail_fragment) {
    private lateinit var binding: DetailFragmentBinding

   private val arg: DetailNewsFragmentArgs by navArgs()

    private lateinit var viewModel: MainViewModel
//    private val TAG = "DetailFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater,container,false)

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = DetailFragmentBinding.bind(view)
//        val myModel = arguments?.getSerializable("article") as Article


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


//        Toast.makeText(requireContext(), "detail: ${arg.article.url})", Toast.LENGTH_LONG).show()

        val news = arg.article
        binding.webView.apply {
            webViewClient = WebViewClient()

            settings.javaScriptEnabled
            settings.javaScriptCanOpenWindowsAutomatically
            settings.loadsImagesAutomatically
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            loadUrl(news.url)
//            loadUrl(myModel.url)

        }
        binding.floatingActionButton.setOnClickListener {
            news.let {article->
                viewModel.saveArticles(article)
            }
            view?.let { it1 -> Snackbar.make(it1, "Article saved successfully", Snackbar.LENGTH_SHORT).show() }
        }

        return binding.root

    }

}