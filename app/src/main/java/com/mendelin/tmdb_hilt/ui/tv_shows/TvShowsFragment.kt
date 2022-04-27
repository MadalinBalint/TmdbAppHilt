package com.mendelin.tmdb_hilt.ui.tv_shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.databinding.FragmentTvShowsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : Fragment() {
    private var binding: FragmentTvShowsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvShowsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles = arrayOf(
            getString(R.string.title_tv_on_the_air),
            getString(R.string.title_popular_tv_shows),
            getString(R.string.title_top_rated_tv_shows)
        )

        binding?.apply {
            tvViewPager.adapter = TvShowsAdapter(this@TvShowsFragment)
            TabLayoutMediator(tvTabLayout, tvViewPager) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}