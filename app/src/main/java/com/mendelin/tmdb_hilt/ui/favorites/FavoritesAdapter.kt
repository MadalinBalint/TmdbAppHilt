package com.mendelin.tmdb_hilt.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemMovieListResultBinding
import com.mendelin.tmdb_hilt.ItemTvListResultBinding
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.FavoriteType
import com.mendelin.tmdb_hilt.common.IDetails
import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.data.model.favorite.MultipleItem
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoritesAdapter(val viewModel: FavoritesViewModel) : ListAdapter<MultipleItem, FavoritesAdapter.MultipleViewHolder>(MultipleDiffCallback()) {
    private val favoritesList: ArrayList<MultipleItem> = ArrayList()

    inner class MultipleViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMovie(movie: MovieListResultEntity) {
            (binding as ItemMovieListResultBinding).apply {
                property = movie
                callback = IDetails {
                    val args = Bundle()
                    args.putInt("movieId", movie.id)

                    movieCard.findNavController().navigate(R.id.movieDetailsFragment, args)
                }

                btnFavoriteMovie.isChecked = true

                btnFavoriteMovie.setOnCheckedChangeListener { _, check ->
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.repository.deleteFavoriteMovie(movie.id)
                        delay(200)
                        viewModel.fetchFavoritesList()
                    }
                }

                executePendingBindings()
            }
        }

        fun bindTvShow(tvShow: TvListResultEntity) {
            (binding as ItemTvListResultBinding).apply {
                property = tvShow

                callback = IDetails {
                    val args = Bundle()
                    args.putString("tvShowName", tvShow.name)
                    args.putInt("tvShowId", tvShow.id)

                    tvListCard.findNavController().navigate(R.id.tvShowDetailsFragment, args)
                }

                btnFavoriteTvShow.isChecked = true

                btnFavoriteTvShow.setOnCheckedChangeListener { _, check ->
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.repository.deleteFavoriteTvShow(tvShow.id)
                        delay(200)
                        viewModel.fetchFavoritesList()
                    }
                }

                executePendingBindings()
            }
        }
    }

    class MultipleDiffCallback : DiffUtil.ItemCallback<MultipleItem>() {
        override fun areItemsTheSame(oldItem: MultipleItem, newItem: MultipleItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MultipleItem, newItem: MultipleItem): Boolean {
            return oldItem.content == newItem.content
        }
    }

    override fun getItemViewType(position: Int): Int {
        return favoritesList[position].type.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleViewHolder {
        return when (viewType) {
            FavoriteType.FAVORITE_MOVIE.value ->
                MultipleViewHolder(ItemMovieListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))

            FavoriteType.FAVORITE_TV_SHOW.value ->
                MultipleViewHolder(ItemTvListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))

            else -> throw IndexOutOfBoundsException("View type $viewType should be between 0..1")
        }
    }

    override fun onBindViewHolder(holder: MultipleViewHolder, position: Int) {
        when (getItemViewType(position)) {
            FavoriteType.FAVORITE_MOVIE.value ->
                holder.bindMovie(favoritesList[position].content as MovieListResultEntity)

            FavoriteType.FAVORITE_TV_SHOW.value ->
                holder.bindTvShow(favoritesList[position].content as TvListResultEntity)
        }
    }

    fun setList(list: List<MultipleItem>) {
        favoritesList.apply {
            clear()
            addAll(list)
        }

        submitList(favoritesList)
        notifyDataSetChanged()
    }
}