package com.example.newsappkotlin.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappkotlin.R
import com.example.newsappkotlin.databinding.SavedFragmentBinding
import com.example.newsappkotlin.ui.adapter.MainRvAdapter

class SavedFragment : Fragment(R.layout.saved_fragment) {
    lateinit var  binding : SavedFragmentBinding
//    lateinit var viewModel: MainViewModel
    lateinit var newsAdapter : MainRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SavedFragmentBinding.bind(view)

//        val apiService = RetrofitHelper.retrofitInstance().create(NewsApi::class.java)
//        val repository = NewsRepository(ArticleDatabase.getDatabaseInstance(requireContext()),apiService)
//        val factory = ViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setUpRecyclerView()
        newsAdapter.onItemClicked {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_detailNewsFragment,bundle)
        }
    }
    private fun setUpRecyclerView() {
        newsAdapter = MainRvAdapter()
        binding.savedRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

}