package com.mendelin.tmdb_hilt.presentation.tv_shows_top_rated

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

class TvTopRatedAdapter(val callback: FavoritesCallback) : PagingDataAdapter<TvListResultEntity, TvTopRatedAdapter.TvTopRatedViewHolder>(TvPopularDiffCallBack()) {

    inner class TvTopRatedViewHolder(var binding: ItemTvListResultBinding) : RecyclerView.ViewHolder(binding.root) {
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

                btnFavoriteTvShow.isChecked = callback.isFavoriteTvShow(tvShow.id)
                btnFavoriteTvShow.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        callback.insertFavoriteTvShow(tvShow)
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

    override fun onBindViewHolder(holder: TvTopRatedViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvTopRatedViewHolder {
        return TvTopRatedViewHolder(ItemTvListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}