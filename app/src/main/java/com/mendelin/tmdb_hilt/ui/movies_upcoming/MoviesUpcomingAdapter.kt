package com.mendelin.tmdb_hilt.ui.movies_upcoming

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
import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.ui.favorites.FavoritesCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesUpcomingAdapter(val callback: FavoritesCallback) : PagingDataAdapter<MovieListResultEntity, MoviesUpcomingAdapter.UpcomingMoviesViewHolder>(UpcomingMoviesDiffCallBack()) {

    inner class UpcomingMoviesViewHolder(var binding: ItemMovieListResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieListResultEntity) {
            binding.apply {
                property = movie

                listener = DetailsListener {
                    val args = Bundle()
                    args.putInt("movieId", movie.id)

                    movieCard.findNavController().navigate(R.id.movieDetailsFragment, args)

                    btnFavoriteMovie.isChecked = callback.isFavoriteMovie(movie.id)
                    btnFavoriteMovie.setOnClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            callback.insertFavoriteMovie(movie)
                        }
                    }
                }
            }

            binding.executePendingBindings()
        }
    }

    class UpcomingMoviesDiffCallBack : DiffUtil.ItemCallback<MovieListResultEntity>() {
        override fun areItemsTheSame(oldItem: MovieListResultEntity, newItem: MovieListResultEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieListResultEntity, newItem: MovieListResultEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: UpcomingMoviesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesViewHolder {
        return UpcomingMoviesViewHolder(ItemMovieListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}