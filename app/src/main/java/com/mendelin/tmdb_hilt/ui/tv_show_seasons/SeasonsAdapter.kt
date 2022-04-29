package com.mendelin.tmdb_hilt.ui.tv_show_seasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemSeasonBinding
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.DetailsListener
import com.mendelin.tmdb_hilt.data.model.rest_api.SeasonItem

class SeasonsAdapter(private val tvName: String, private val tvId: Int) : ListAdapter<SeasonItem, SeasonsAdapter.SeasonViewHolder>(SeasonDiffCallBack()) {
    private val castList: ArrayList<SeasonItem> = ArrayList()

    class SeasonViewHolder(var binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, id: Int, tvShowSeason: SeasonItem) {
            binding.apply {
                season = tvShowSeason
                callback = DetailsListener {
                    val args = Bundle().apply {
                        putString("tvShowName", name)
                        putInt("tvShowId", id)
                        putInt("season", tvShowSeason.season_number)
                    }

                    seasonCard.findNavController().navigate(R.id.tvShowSeasonFragment, args)
                }
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
        holder.bind(tvName, tvId, item)
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