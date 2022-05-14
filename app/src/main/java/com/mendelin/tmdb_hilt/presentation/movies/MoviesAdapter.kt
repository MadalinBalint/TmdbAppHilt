package com.mendelin.tmdb_hilt.presentation.movies

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mendelin.tmdb_hilt.presentation.movies_now_playing.MoviesNowPlayingFragment
import com.mendelin.tmdb_hilt.presentation.movies_popular.MoviesPopularFragment
import com.mendelin.tmdb_hilt.presentation.movies_upcoming.MoviesUpcomingFragment

class MoviesAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
       return when (position) {
            0 -> MoviesNowPlayingFragment()
            1 -> MoviesPopularFragment()
            else -> MoviesUpcomingFragment()
        }
    }
}