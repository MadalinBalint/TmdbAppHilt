package com.mendelin.tmdb_hilt.ui.tv_shows_popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemTvListResultBinding
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.IDetails
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultItem
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvPopularAdapter @Inject constructor(val repository: FavoritesRepository): PagingDataAdapter<TvListResultItem, TvPopularAdapter.TvPopularViewHolder>(TvPopularDiffCallBack()) {

   inner class TvPopularViewHolder(var binding: ItemTvListResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvListResultItem) {
            binding.property = tvShow

            binding.callback = IDetails {
                val args = Bundle()
                args.putString("tvShowName", tvShow.name)
                args.putInt("tvShowId", tvShow.id)

                binding.tvListCard.findNavController().navigate(R.id.tvShowDetailsFragment, args)
            }

            binding.btnFavoriteTvShow.isChecked = repository.isFavoriteTvShow(tvShow.id)

            binding.btnFavoriteTvShow.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.insertFavoriteTvShow(tvShow)
                }
            }

            binding.executePendingBindings()
        }
    }

    class TvPopularDiffCallBack : DiffUtil.ItemCallback<TvListResultItem>() {
        override fun areItemsTheSame(oldItem: TvListResultItem, newItem: TvListResultItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TvListResultItem, newItem: TvListResultItem): Boolean {
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