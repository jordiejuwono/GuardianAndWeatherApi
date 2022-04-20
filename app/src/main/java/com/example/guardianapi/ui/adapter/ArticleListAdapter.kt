package com.example.guardianapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.guardianapi.R
import com.example.guardianapi.data.network.model.guardianapi.Result
import com.example.guardianapi.databinding.ArticleRowBinding

class ArticleListAdapter(private val articleItemClickListener: ArticleItemClickListener) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    private var items: MutableList<Result> = mutableListOf()

    class ArticleViewHolder(val binding: ArticleRowBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ArticleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentPosition = items[position]
        holder.binding.tvArticleTitle.text = currentPosition.webTitle
        holder.binding.tvCategories.text = currentPosition.sectionName
        holder.binding.tvArticleDate.text = (currentPosition.articleDate)?.slice(0..9)
        holder.binding.llArticleRow.setOnClickListener {
            articleItemClickListener.onItemClick(currentPosition)
        }
        if(currentPosition.fields?.thumbnail == null){
            holder.binding.ivGuardian.load(R.drawable.ic_guardian){
                crossfade(false)
                placeholder(R.drawable.ic_guardian)
            }
        } else {
            holder.binding.ivGuardian.load(currentPosition.fields?.thumbnail){
                crossfade(false)
                placeholder(R.drawable.ic_guardian)
            }
        }
    }

    interface ArticleItemClickListener{
        fun onItemClick(items: Result)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<Result>){
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}