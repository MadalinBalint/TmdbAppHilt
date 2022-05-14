package com.mendelin.tmdb_hilt.presentation.tv_shows_popular

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
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity
import com.mendelin.tmdb_hilt.presentation.favorites.FavoritesCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvPopularAdapter(val callback: FavoritesCallback) : PagingDataAdapter<TvListResultEntity, TvPopularAdapter.TvPopularViewHolder>(TvPopularDiffCallBack()) {

    inner class TvPopularViewHolder(var binding: ItemTvListResultBinding) : RecyclerView.ViewHolder(binding.root) {
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

                btnFavoriteTvShow.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (btnFavoriteTvShow.isChecked) {
                            callback.insertFavoriteTvShow(tvShow)
                        } else {
                            callback.deleteFavoriteTvShow(tvShow.id)
                        }
                    }
                }
            }

            binding.executePendingBindings()
        }
    }

    class TvPopularDiffCallBack : DiffUtil.ItemCallback<TvListResultEntity>() {
        override fun areItemsTheSame(oldItem: TvListResultEntity, newItem: TvListResultEntity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TvListResultEntity, newItem: TvListResultEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: TvPopularViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvPopularViewHolder {
        return TvPopularViewHolder(ItemTvListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}