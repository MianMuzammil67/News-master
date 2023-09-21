package com.example.newsappkotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappkotlin.R
import com.example.newsappkotlin.api.NewsApi
import com.example.newsappkotlin.api.RetrofitHelper
import com.example.newsappkotlin.databinding.NewsFragmentBinding
import com.example.newsappkotlin.db.ArticleDatabase
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.ui.adapter.MainRvAdapter
import com.example.newsappkotlin.ui.viewModel.MainViewModel
import com.example.newsappkotlin.ui.viewModel.ViewModelFactory
import com.example.newsappkotlin.util.Resource

class NewsFragment : Fragment(R.layout.news_fragment) {
    private lateinit var binding: NewsFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var newsAdapter: MainRvAdapter
    private val TAG = "NewsFragment"
    private val NewsCountory = "us"
    private val NewsPage = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NewsFragmentBinding.bind(view)

//        viewModel = (activity as MainActivity).viewModel

        val apiService = RetrofitHelper.retrofitInstance().create(NewsApi::class.java)
        val repository =
            NewsRepository(ArticleDatabase.getDatabaseInstance(requireContext()), apiService)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.getNews(NewsCountory, NewsPage)
        setUpRecyclerView()

        newsAdapter.onItemClicked {article->
            val action = NewsFragmentDirections.actionNewsFragmentToDetailNewsFragment(article)
            findNavController().navigate(action)
        }

        viewModel.articleLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        hideProgressBar()
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    response.data?.let {
                        hideProgressBar()
                        Log.e(TAG, "an error occur: $it ")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun setUpRecyclerView() {
        newsAdapter = MainRvAdapter()
        binding.rvMain.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

}