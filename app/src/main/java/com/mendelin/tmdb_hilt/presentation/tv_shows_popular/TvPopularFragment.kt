package com.mendelin.tmdb_hilt.presentation.tv_shows_popular

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
import com.mendelin.tmdb_hilt.common.Utils.getFavoritesCallback
import com.mendelin.tmdb_hilt.common.Utils.setUiState
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.databinding.FragmentTvPopularBinding
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
class TvPopularFragment : Fragment() {
    @Inject
    lateinit var preferences: PreferencesRepository

    private val viewModel: TvPopularViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private var binding: FragmentTvPopularBinding? = null
    lateinit var tvPopularAdapter: TvPopularAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTvPopularBinding.inflate(layoutInflater, container, false)
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
            binding?.recyclerPopularTvShows?.apply {
                if (viewModel.firstLoad) delay(200)
                scrollToPosition(preferences.getPostion(PreferencesRepository.KEY_TV_POPULAR, 0).first())
            }
        }
    }

    override fun onPause() {
        val manager = binding?.recyclerPopularTvShows?.layoutManager as LinearLayoutManager
        val pos = if (manager.findFirstCompletelyVisibleItemPosition() > -1) manager.findFirstCompletelyVisibleItemPosition() else manager.findFirstVisibleItemPosition()

        /* Save last position */
        CoroutineScope(Dispatchers.IO).launch {
            preferences.updatePosition(PreferencesRepository.KEY_TV_POPULAR, pos)
        }

        viewModel.firstLoad = false

        super.onPause()
    }

    private fun setupUI() {
        tvPopularAdapter = TvPopularAdapter(favoritesViewModel.getFavoritesCallback())

        binding?.recyclerPopularTvShows?.apply {
            adapter = tvPopularAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = true

            addItemDecoration(
                MarginItemVerticalDecoration(
                    resources.getDimension(R.dimen.recyclerview_margin_horizontal).toInt(),
                    resources.getDimension(R.dimen.recyclerview_margin_vertical).toInt()
                )
            )
        }

        binding?.swipePopularTvShows?.setOnRefreshListener {
            tvPopularAdapter.refresh()
            binding?.swipePopularTvShows?.isRefreshing = false
        }

        viewModel.fetchPopularTvShows()
    }

    private fun observeViewModel() {
        viewModel.getErrorFilter().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding?.frameLayout!!, it, Snackbar.LENGTH_LONG)
                    .setAction("Retry") {
                        tvPopularAdapter.refresh()
                    }
                    .show()
                viewModel.onErrorHandled()
            }
        }

        viewModel.getLoadingObservable().observe(viewLifecycleOwner) {
            binding?.progressPopularTvShows?.visibility = if (it == true) View.VISIBLE else View.INVISIBLE
        }

        lifecycleScope.launch {
            tvPopularAdapter.loadStateFlow.collectLatest { state ->
                viewModel.setUiState(state)
            }
        }

        viewModel.popularTvShows.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                tvPopularAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}