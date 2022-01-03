package com.ims.imdb_kotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ims.imdb_kotlin.databinding.GenreItemBinding

class GenreAdapter(
    private val list: ArrayList<String>
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder?>() {

    inner class GenreViewHolder(
        binding: GenreItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val binding : GenreItemBinding = binding
        fun bindData(genre: String?) {
            binding.genreText.text = genre
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreAdapter.GenreViewHolder {
        val binding: GenreItemBinding =
            GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreAdapter.GenreViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bindData(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}