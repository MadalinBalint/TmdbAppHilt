package com.mendelin.tmdb_hilt.presentation.movies_now_playing

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

class MoviesNowPlayingAdapter(val callback: FavoritesCallback) : PagingDataAdapter<MovieListResultEntity, MoviesNowPlayingAdapter.NowPlayingMoviesViewHolder>(NowPlayingMoviesDiffCallBack()) {

    inner class NowPlayingMoviesViewHolder(var binding: ItemMovieListResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieListResultEntity) {
            binding.apply {
                property = movie
                listener = DetailsListener {
                    val args = Bundle()
                    args.putInt("movieId", movie.id)

                    movieCard.findNavController().navigate(R.id.movieDetailsFragment, args)
                }

                btnFavoriteMovie.isChecked = callback.isFavoriteMovie(movie.id)
                btnFavoriteMovie.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        callback.insertFavoriteMovie(movie)
                    }
                }
            }

            binding.executePendingBindings()
        }
    }

    class NowPlayingMoviesDiffCallBack : DiffUtil.ItemCallback<MovieListResultEntity>() {
        override fun areItemsTheSame(oldItem: MovieListResultEntity, newItem: MovieListResultEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieListResultEntity, newItem: MovieListResultEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: NowPlayingMoviesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMoviesViewHolder {
        return NowPlayingMoviesViewHolder(ItemMovieListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}