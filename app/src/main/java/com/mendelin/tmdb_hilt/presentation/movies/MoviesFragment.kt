package com.mendelin.tmdb_hilt.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private var binding: FragmentMoviesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles = arrayOf(
            getString(R.string.title_now_playing_movies),
            getString(R.string.title_popular_movies),
            getString(R.string.title_upcoming_movies)
        )

        binding?.apply {
            moviesViewPager.adapter = MoviesAdapter(this@MoviesFragment)
            TabLayoutMediator(moviesTabLayout, moviesViewPager) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}