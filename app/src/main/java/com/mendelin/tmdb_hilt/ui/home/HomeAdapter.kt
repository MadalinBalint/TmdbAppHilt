package com.mendelin.tmdb_hilt.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemMovieListResultBinding
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.IDetails
import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultItem
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeAdapter @Inject constructor(val repository: FavoritesRepository) : PagingDataAdapter<MovieListResultItem, HomeAdapter.TopRatedMoviesViewHolder>(TopRatedMoviesDiffCallBack()) {
    inner class TopRatedMoviesViewHolder(var binding: ItemMovieListResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieListResultItem) {
            binding.property = movie

            binding.callback = IDetails {
                val args = Bundle()
                args.putInt("movieId", movie.id)

                binding.movieCard.findNavController().navigate(R.id.movieDetailsFragment, args)
            }

            binding.btnFavoriteMovie.isChecked = repository.isFavoriteMovie(movie.id)

            binding.btnFavoriteMovie.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.insertFavoriteMovie(movie)
                }
            }

            binding.executePendingBindings()
        }
    }

    class TopRatedMoviesDiffCallBack : DiffUtil.ItemCallback<MovieListResultItem>() {
        override fun areItemsTheSame(oldItem: MovieListResultItem, newItem: MovieListResultItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieListResultItem, newItem: MovieListResultItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: TopRatedMoviesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMoviesViewHolder {
        return TopRatedMoviesViewHolder(ItemMovieListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}