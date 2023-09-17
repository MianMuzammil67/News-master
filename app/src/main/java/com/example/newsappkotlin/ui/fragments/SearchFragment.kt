package com.example.newsappkotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappkotlin.R
import com.example.newsappkotlin.api.NewsApi
import com.example.newsappkotlin.api.RetrofitHelper
import com.example.newsappkotlin.databinding.SearchFragmentBinding
import com.example.newsappkotlin.db.ArticleDatabase
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.ui.adapter.MainRvAdapter
import com.example.newsappkotlin.ui.viewModel.MainViewModel
import com.example.newsappkotlin.ui.viewModel.ViewModelFactory
import com.example.newsappkotlin.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.search_fragment) {
    private lateinit var binding: SearchFragmentBinding
    private lateinit var newsAdapter: MainRvAdapter
    private val TAG = "SearchFragment"
    private val NewsPage = 1

    lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SearchFragmentBinding.bind(view)
//        viewModel = (activity as MainActivity).viewModel

        val apiService = RetrofitHelper.retrofitInstance().create(NewsApi::class.java)
        val repository =
            NewsRepository(ArticleDatabase.getDatabaseInstance(requireContext()), apiService)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        newsAdapter.onItemClicked {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_detailNewsFragment,bundle)
        }

        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(200)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString(), NewsPage)
                    }
                }
            }
        }
        viewModel.searchLiveData.observe(viewLifecycleOwner) { response ->

            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.data.let {
                        Log.e(TAG, "an error occur: $it ")
                    }
                }

                is Resource.Loading -> {

                    showProgressBar()
                }

            }

        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        newsAdapter = MainRvAdapter()
        binding.searchRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}