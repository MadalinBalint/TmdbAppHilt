package com.mendelin.tmdb_hilt.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemMovieListResultBinding
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.DetailsListener
import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.presentation.favorites.FavoritesCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeAdapter constructor(val callback: FavoritesCallback) : PagingDataAdapter<MovieListResultEntity, HomeAdapter.TopRatedMoviesViewHolder>(TopRatedMoviesDiffCallBack()) {
    inner class TopRatedMoviesViewHolder(var binding: ItemMovieListResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieListResultEntity) {
            binding.apply {
                property = movie

                listener = DetailsListener {
                    val args = Bundle()
                    args.putInt("movieId", movie.id)

                    movieCard.findNavController().navigate(R.id.movieDetailsFragment, args)
                }

                btnFavoriteMovie.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (btnFavoriteMovie.isChecked) {
                            callback.insertFavoriteMovie(movie)
                        } else {
                            callback.deleteFavoriteMovie(movie.id)
                        }
                    }
                }
            }

            binding.executePendingBindings()
        }
    }

    class TopRatedMoviesDiffCallBack : DiffUtil.ItemCallback<MovieListResultEntity>() {
        override fun areItemsTheSame(oldItem: MovieListResultEntity, newItem: MovieListResultEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieListResultEntity, newItem: MovieListResultEntity): Boolean {
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