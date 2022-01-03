package com.ims.imdb_kotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ims.imdb_kotlin.databinding.ResultItemBinding
import com.ims.imdb_kotlin.models.Result
import com.ims.imdb_kotlin.utils.Helper

class SearchAdapter(
    private val listener: onSearchResultClickListener?, results: List<Result>
) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder?>() {
    private val results: List<Result> = results
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ResultItemBinding.inflate(LayoutInflater.from(parent.context))
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bindData(results[position])
            holder.itemView.setOnClickListener { v: View? ->
                listener?.onResultClick(
                    results[position]
                )
            }
        }
    }


    inner class SearchViewHolder(var binding: ResultItemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bindData(result: Result?) {
            if (result != null) {
                result.poster_path?.let {
                    Helper.loadImage(
                        binding.root.context,
                        it, binding.resultImage
                    )
                }
                binding.rating.text = result.vote_average.toString() + ""
                binding.title.text = result.original_title
            }
        }
    }

    interface onSearchResultClickListener {
        fun onResultClick(result: Result?)
    }

    override fun getItemCount(): Int {
        return results.size
    }
}