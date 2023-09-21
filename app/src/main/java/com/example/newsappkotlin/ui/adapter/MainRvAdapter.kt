package com.example.newsappkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappkotlin.R
import com.example.newsappkotlin.databinding.RvMainLayoutBinding
import com.example.newsappkotlin.models.Article

class MainRvAdapter() : RecyclerView.Adapter<MainRvAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.rv_main_layout,parent,false)
            RvMainLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articleList = differ.currentList[position]
        holder.itemView.apply {
            holder.binding.apply {
                tvAuthor.text = articleList.source.name
                tvDate.text = articleList.publishedAt
                tvTittle.text = articleList.title
            }
            Glide.with(this).load(articleList.urlToImage).placeholder(R.drawable.hourglass)
                .into(holder.binding.newsImage)
            setOnClickListener {
                itemClick?.let { it(articleList) }
            }
        }
    }

    private var itemClick: ((Article) -> Unit)? = null

    fun onItemClicked(listener: (Article) -> Unit) {
        itemClick = listener

    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtilCallback)

    inner class ViewHolder(val binding: RvMainLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}