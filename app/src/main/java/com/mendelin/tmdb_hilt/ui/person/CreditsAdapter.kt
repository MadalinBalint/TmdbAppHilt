package com.mendelin.tmdb_hilt.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.ItemCreditBinding
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.DetailsListener
import com.mendelin.tmdb_hilt.data.model.rest_api.MovieCreditsCastItem

class CreditsAdapter : ListAdapter<MovieCreditsCastItem, CreditsAdapter.CreditsViewHolder>(CreditsDiffCallBack()) {
    private val creditsList: ArrayList<MovieCreditsCastItem> = ArrayList()

    class CreditsViewHolder(var binding: ItemCreditBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(credit: MovieCreditsCastItem) {
            binding.apply {
                cast = credit
                listener = DetailsListener {
                    val args = Bundle()
                    args.putInt("movieId", credit.id)

                    creditsCard.findNavController().navigate(R.id.movieDetailsFragment, args)
                }
            }

            binding.executePendingBindings()
        }
    }

    class CreditsDiffCallBack : DiffUtil.ItemCallback<MovieCreditsCastItem>() {
        override fun areItemsTheSame(oldItem: MovieCreditsCastItem, newItem: MovieCreditsCastItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieCreditsCastItem, newItem: MovieCreditsCastItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {
        val item = creditsList[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        return CreditsViewHolder(ItemCreditBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun setList(list: List<MovieCreditsCastItem>) {
        creditsList.apply {
            clear()
            addAll(list)
        }

        submitList(creditsList)
        notifyDataSetChanged()
    }
}