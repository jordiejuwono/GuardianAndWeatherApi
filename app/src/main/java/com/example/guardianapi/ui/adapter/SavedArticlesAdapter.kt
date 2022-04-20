package com.example.guardianapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.guardianapi.R
import com.example.guardianapi.data.local.room.entity.Articles
import com.example.guardianapi.databinding.ArticleRowBinding

class SavedArticlesAdapter(private val savedArticlesClickListener: SavedArticlesClickListener) :
    RecyclerView.Adapter<SavedArticlesAdapter.SavedArticlesViewHolder>() {

    val items: MutableList<Articles> = mutableListOf()

    class SavedArticlesViewHolder(val binding: ArticleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    interface SavedArticlesClickListener {
        fun onItemClick(items: Articles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedArticlesViewHolder {
        return SavedArticlesViewHolder(
            ArticleRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SavedArticlesViewHolder, position: Int) {
        val currentPosition = items[position]
        holder.binding.tvArticleTitle.text = currentPosition.webTitle

        if (currentPosition.image == null) {
            holder.binding.ivGuardian.load(R.drawable.ic_guardian) {
                placeholder(R.drawable.ic_guardian)
            }
        } else {
            holder.binding.ivGuardian.load(currentPosition.image)
        }

        holder.binding.tvArticleDate.text = currentPosition.articleDate?.slice(0..9)
        holder.binding.tvCategories.text = currentPosition.sectionName
        holder.binding.llArticleRow.setOnClickListener {
            savedArticlesClickListener.onItemClick(currentPosition)
        }
    }

    override fun getItemCount(): Int = items.size

    private fun clearData() {
        this.items.clear()
    }

    fun setData(data: List<Articles>) {
        clearData()
        items.addAll(data)
        notifyDataSetChanged()
    }
}