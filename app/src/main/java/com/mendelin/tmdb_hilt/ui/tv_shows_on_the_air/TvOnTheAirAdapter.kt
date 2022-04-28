package com.mendelin.tmdb_hilt.ui.tv_shows_on_the_air

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemTvListResultBinding
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.DetailsListener
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultEntity
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvOnTheAirAdapter @Inject constructor(val repository: FavoritesRepository) : PagingDataAdapter<TvListResultEntity, TvOnTheAirAdapter.NowPlayingMoviesViewHolder>(NowPlayingMoviesDiffCallBack()) {

    inner class NowPlayingMoviesViewHolder(var binding: ItemTvListResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvListResultEntity) {
            binding.apply {
                property = tvShow

                listener = DetailsListener {
                    val args = Bundle().apply {
                        putString("tvShowName", tvShow.name)
                        putInt("tvShowId", tvShow.id)
                    }

                    tvListCard.findNavController().navigate(R.id.tvShowDetailsFragment, args)
                }

                btnFavoriteTvShow.isChecked = repository.isFavoriteTvShow(tvShow.id)

                btnFavoriteTvShow.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        repository.insertFavoriteTvShow(tvShow)
                    }
                }
            }

            binding.executePendingBindings()
        }
    }

    class NowPlayingMoviesDiffCallBack : DiffUtil.ItemCallback<TvListResultEntity>() {
        override fun areItemsTheSame(oldItem: TvListResultEntity, newItem: TvListResultEntity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TvListResultEntity, newItem: TvListResultEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: NowPlayingMoviesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMoviesViewHolder {
        return NowPlayingMoviesViewHolder(ItemTvListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}