package com.mendelin.tmdb_hilt.ui.tv_show_season_episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.databinding.FragmentTvShowSeasonEpisodesBinding
import com.mendelin.tmdb_hilt.ui.custom_view.MarginItemHorizontalDecoration
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TvShowSeasonEpisodesFragment : Fragment() {
    private val viewModel by viewModels<TvShowSeasonEpisodesViewModel>()

    private var binding: FragmentTvShowSeasonEpisodesBinding? = null
    private lateinit var seasonsEpisodesAdapter: SeasonEpisodesAdapter
    private val args: TvShowSeasonEpisodesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvShowSeasonEpisodesBinding.inflate(layoutInflater, container, false)
        binding?.viewmodel = viewModel
        binding?.tvShowName = args.tvShowName
        binding?.lifecycleOwner = viewLifecycleOwner

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seasonsEpisodesAdapter = SeasonEpisodesAdapter()

        binding?.recyclerEpisodes?.apply {
            adapter = seasonsEpisodesAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = true

            addItemDecoration(
                MarginItemHorizontalDecoration(
                    0,
                    resources.getDimension(R.dimen.recyclerview_margin_vertical).toInt()
                )
            )
        }

        Timber.d("TV Show name = ${args.tvShowName}")
        Timber.d("TV Show ID = ${args.tvShowId}")
        Timber.d("TV Show season = ${args.season}")

        viewModel.fetchTvShowDetails(args.tvShowId, args.season)

        viewModel.details.observe(viewLifecycleOwner) {
            Timber.d(it.toString())
        }

        viewModel.seasons.observe(viewLifecycleOwner) {
            Timber.d(it.toString())

            seasonsEpisodesAdapter.setList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}