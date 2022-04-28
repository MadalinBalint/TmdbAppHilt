package com.mendelin.tmdb_hilt.ui.movies_now_playing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemMovieListResultBinding
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.IDetails
import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesNowPlayingAdapter @Inject constructor(val repository: FavoritesRepository) : PagingDataAdapter<MovieListResultEntity, MoviesNowPlayingAdapter.NowPlayingMoviesViewHolder>(NowPlayingMoviesDiffCallBack()) {
    inner class NowPlayingMoviesViewHolder(var binding: ItemMovieListResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieListResultEntity) {
            binding.property = movie
            binding.callback = IDetails {
                val navController = Navigation.findNavController(binding.root)

                val args = Bundle()
                args.putInt("movieId", movie.id)

                navController.navigate(R.id.movieDetailsFragment, args)
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