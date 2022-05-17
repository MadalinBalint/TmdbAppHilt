package com.mendelin.tmdb_hilt.presentation.movies_now_playing

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
import com.mendelin.tmdb_hilt.databinding.FragmentMoviesNowPlayingBinding
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
class MoviesNowPlayingFragment : Fragment() {
    @Inject
    lateinit var preferences: PreferencesRepository

    private val viewModel: MoviesNowPlayingViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private var binding: FragmentMoviesNowPlayingBinding? = null
    lateinit var moviesNowPlayingAdapter: MoviesNowPlayingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesNowPlayingBinding.inflate(layoutInflater, container, false)
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
            binding?.recyclerNowPlayingMovies?.apply {
                if (viewModel.firstLoad) delay(200)
                scrollToPosition(preferences.getPostion(PreferencesRepository.KEY_MOVIES_NOW_PLAYING, 0).first())
            }
        }
    }

    override fun onPause() {
        val manager = binding?.recyclerNowPlayingMovies?.layoutManager as LinearLayoutManager
        val pos = if (manager.findFirstCompletelyVisibleItemPosition() > -1) manager.findFirstCompletelyVisibleItemPosition() else manager.findFirstVisibleItemPosition()

        /* Save last position */
        CoroutineScope(Dispatchers.IO).launch {
            preferences.updatePosition(PreferencesRepository.KEY_MOVIES_NOW_PLAYING, pos)
        }

        viewModel.firstLoad = false

        super.onPause()
    }

    private fun setupUI() {
        moviesNowPlayingAdapter = MoviesNowPlayingAdapter(favoritesViewModel.getFavoritesCallback())

        binding?.recyclerNowPlayingMovies?.apply {
            adapter = moviesNowPlayingAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = true

            addItemDecoration(
                MarginItemVerticalDecoration(
                    resources.getDimension(R.dimen.recyclerview_margin_horizontal).toInt(),
                    resources.getDimension(R.dimen.recyclerview_margin_vertical).toInt()
                )
            )
        }

        binding?.swipeNowPlayingMovies?.setOnRefreshListener {
            moviesNowPlayingAdapter.refresh()
            binding?.swipeNowPlayingMovies?.isRefreshing = false
        }

        viewModel.fetchNowPlayingMovies()
    }

    private fun observeViewModel() {
        viewModel.getErrorFilter().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding?.frameLayout!!, it, Snackbar.LENGTH_LONG)
                    .setAction("Retry") {
                        moviesNowPlayingAdapter.refresh()
                    }
                    .show()
                viewModel.onErrorHandled()
            }
        }

        viewModel.getLoadingObservable().observe(viewLifecycleOwner) {
            binding?.progressNowPlayingMovies?.visibility = if (it == true) View.VISIBLE else View.INVISIBLE
        }

        lifecycleScope.launch {
            moviesNowPlayingAdapter.loadStateFlow.collectLatest { state ->
                viewModel.setUiState(state)
            }
        }

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                moviesNowPlayingAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}