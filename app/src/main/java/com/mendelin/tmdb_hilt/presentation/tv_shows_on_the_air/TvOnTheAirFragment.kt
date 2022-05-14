package com.mendelin.tmdb_hilt.presentation.tv_shows_on_the_air

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.Utils
import com.mendelin.tmdb_hilt.common.Utils.setFavoriteTvShows
import com.mendelin.tmdb_hilt.common.Utils.setUiState
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.databinding.FragmentTvOnTheAirBinding
import com.mendelin.tmdb_hilt.presentation.custom_view.MarginItemVerticalDecoration
import com.mendelin.tmdb_hilt.presentation.favorites.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TvOnTheAirFragment : Fragment() {
    @Inject
    lateinit var preferences: PreferencesRepository

    private val viewModel: TvOnTheAirViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private var binding: FragmentTvOnTheAirBinding? = null
    lateinit var tvOnTheAirAdapter: TvOnTheAirAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvOnTheAirBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()

        /* Restore last position */
        CoroutineScope(Dispatchers.Main).launch {
            binding?.recyclerOnTheAirTvShows?.apply {
                if (viewModel.firstLoad) delay(200)
                scrollToPosition(preferences.getPostion(PreferencesRepository.KEY_TV_ON_THE_AIR, 0).first())
            }
        }
    }

    override fun onPause() {
        val manager = binding?.recyclerOnTheAirTvShows?.layoutManager as LinearLayoutManager
        val pos = if (manager.findFirstCompletelyVisibleItemPosition() > -1) manager.findFirstCompletelyVisibleItemPosition() else manager.findFirstVisibleItemPosition()

        /* Save last position */
        CoroutineScope(Dispatchers.IO).launch {
            preferences.updatePosition(PreferencesRepository.KEY_TV_ON_THE_AIR, pos)
        }

        viewModel.firstLoad = false

        super.onPause()
    }

    private fun setupUI() {
        tvOnTheAirAdapter = TvOnTheAirAdapter(Utils.getFavoritesCallback(favoritesViewModel))

        binding?.recyclerOnTheAirTvShows?.apply {
            adapter = tvOnTheAirAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = true

            addItemDecoration(
                MarginItemVerticalDecoration(
                    resources.getDimension(R.dimen.recyclerview_margin_horizontal).toInt(),
                    resources.getDimension(R.dimen.recyclerview_margin_vertical).toInt()
                )
            )
        }

        binding?.swipeOnTheAirTvShows?.setOnRefreshListener {
            tvOnTheAirAdapter.refresh()
            binding?.swipeOnTheAirTvShows?.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.getErrorFilter().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding?.frameLayout!!, it, Snackbar.LENGTH_LONG)
                    .setAction("Retry") {
                        tvOnTheAirAdapter.refresh()
                    }
                    .show()
                viewModel.onErrorHandled()
            }
        }

        viewModel.getLoadingObservable().observe(viewLifecycleOwner) {
            binding?.progressOnTheAirTvShows?.visibility = if (it == true) View.VISIBLE else View.INVISIBLE
        }

        lifecycleScope.launch {
            tvOnTheAirAdapter.loadStateFlow.collectLatest { state ->
                state.setUiState(viewModel)
            }
        }

        lifecycleScope.launch {
            viewModel.onTheAirTvShows.collectLatest { pagingData ->
                tvOnTheAirAdapter.submitData(pagingData.setFavoriteTvShows(viewModel.favorites))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}