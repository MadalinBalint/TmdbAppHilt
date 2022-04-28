package com.mendelin.tmdb_hilt.ui.tv_shows_top_rated

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
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultEntity
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvTopRatedAdapter @Inject constructor(val repository: FavoritesRepository) : PagingDataAdapter<TvListResultEntity, TvTopRatedAdapter.TvTopRatedViewHolder>(TvPopularDiffCallBack()) {

    inner class TvTopRatedViewHolder(var binding: ItemTvListResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvListResultEntity) {
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