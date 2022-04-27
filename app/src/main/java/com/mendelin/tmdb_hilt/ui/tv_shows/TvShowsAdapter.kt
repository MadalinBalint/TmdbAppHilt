package com.mendelin.tmdb_hilt.ui.tv_shows

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mendelin.tmdb_hilt.ui.tv_shows_on_the_air.TvOnTheAirFragment
import com.mendelin.tmdb_hilt.ui.tv_shows_popular.TvPopularFragment
import com.mendelin.tmdb_hilt.ui.tv_shows_top_rated.TvTopRatedFragment

class TvShowsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TvOnTheAirFragment()
            1 -> TvPopularFragment()
            else -> TvTopRatedFragment()
        }
    }
}