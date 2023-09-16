package com.example.newsappkotlin.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsappkotlin.R
import com.example.newsappkotlin.ui.MainActivity
import com.example.newsappkotlin.ui.viewModel.MainViewModel

class DetailNewsFragment: Fragment(R.layout.detail_fragment) {

    lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel



    }
}