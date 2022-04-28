package com.mendelin.tmdb_hilt.ui.tv_show_season_episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemEpisodeBinding
import com.mendelin.tmdb_hilt.common.IDetails
import com.mendelin.tmdb_hilt.data.model.rest_api.EpisodeItem

class SeasonEpisodesAdapter : ListAdapter<EpisodeItem, SeasonEpisodesAdapter.SeasonEpisodeViewHolder>(SeasonEpisodeDiffCallBack()) {
    private val castList: ArrayList<EpisodeItem> = ArrayList()

    class SeasonEpisodeViewHolder(var binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: EpisodeItem) {
            binding.episode = episode
            binding.callback = IDetails {
                /*val args = Bundle()
                args.putInt("seasonId", season.id)

                binding.episodeCard.findNavController().navigate(R.id.personFragment, args)*/
            }

            binding.executePendingBindings()
        }
    }

    class SeasonEpisodeDiffCallBack : DiffUtil.ItemCallback<EpisodeItem>() {
        override fun areItemsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: SeasonEpisodeViewHolder, position: Int) {
        val item = castList[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonEpisodeViewHolder {
        return SeasonEpisodeViewHolder(ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun setList(list: List<EpisodeItem>) {
        castList.apply {
            clear()
            addAll(list)
        }

        submitList(castList)
        notifyDataSetChanged()
    }
}