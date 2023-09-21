package com.example.newsappkotlin.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappkotlin.R
import com.example.newsappkotlin.api.NewsApi
import com.example.newsappkotlin.api.RetrofitHelper
import com.example.newsappkotlin.databinding.SavedFragmentBinding
import com.example.newsappkotlin.db.ArticleDatabase
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.ui.adapter.MainRvAdapter
import com.example.newsappkotlin.ui.viewModel.MainViewModel
import com.example.newsappkotlin.ui.viewModel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SavedFragment : Fragment(R.layout.saved_fragment) {
    private lateinit var  binding : SavedFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var newsAdapter : MainRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SavedFragmentBinding.bind(view)

        val apiService = RetrofitHelper.retrofitInstance().create(NewsApi::class.java)
        val repository = NewsRepository(ArticleDatabase.getDatabaseInstance(requireContext()),apiService)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setUpRecyclerView()

        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it)
        })
        newsAdapter.onItemClicked {
//            val bundle = Bundle().apply {
//                putSerializable("article",it.url)
//            }
//            findNavController().navigate(R.id.action_savedFragment_to_detailNewsFragment,bundle)
            val action = SavedFragmentDirections.actionSavedFragmentToDetailNewsFragment(it)
            findNavController().navigate(action)

        }
        val itemTouchCallback = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteSavedArticle(article)

                Snackbar.make(view,"Deleted Successfully",Snackbar.LENGTH_LONG).
                apply {
                    setAction("Undo"){
                        viewModel.saveArticles(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchCallback).apply {
            attachToRecyclerView(binding.savedRv)
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