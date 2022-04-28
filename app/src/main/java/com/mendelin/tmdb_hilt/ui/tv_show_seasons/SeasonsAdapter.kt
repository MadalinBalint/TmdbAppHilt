package com.mendelin.tmdb_hilt.ui.tv_show_seasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemSeasonBinding
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.IDetails
import com.mendelin.tmdb_hilt.data.model.rest_api.SeasonItem

class SeasonsAdapter(val tv_name: String, val tv_id: Int) : ListAdapter<SeasonItem, SeasonsAdapter.SeasonViewHolder>(SeasonDiffCallBack()) {
    private val castList: ArrayList<SeasonItem> = ArrayList()

    class SeasonViewHolder(var binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, id: Int, season: SeasonItem) {
            binding.season = season
            binding.callback = IDetails {
                val args = Bundle()
                args.putString("tvShowName", name)
                args.putInt("tvShowId", id)
                args.putInt("season", season.season_number)

                binding.seasonCard.findNavController().navigate(R.id.tvShowSeasonFragment, args)
            }

            binding.executePendingBindings()
        }
    }

    class SeasonDiffCallBack : DiffUtil.ItemCallback<SeasonItem>() {
        override fun areItemsTheSame(oldItem: SeasonItem, newItem: SeasonItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: SeasonItem, newItem: SeasonItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val item = castList[position]
        holder.bind(tv_name, tv_id, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun setList(list: List<SeasonItem>) {
        castList.apply {
            clear()
            addAll(list)
        }

        submitList(castList)
        notifyDataSetChanged()
    }
}