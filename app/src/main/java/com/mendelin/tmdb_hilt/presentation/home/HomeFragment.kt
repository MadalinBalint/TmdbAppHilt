package com.mendelin.tmdb_hilt.presentation.home

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
import com.mendelin.tmdb_hilt.common.Utils.setFavoriteMovies
import com.mendelin.tmdb_hilt.common.Utils.setUiState
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.databinding.FragmentHomeBinding
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
class HomeFragment : Fragment() {
    @Inject
    lateinit var preferences: PreferencesRepository

    private val homeViewModel by viewModels<HomeViewModel>()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private var binding: FragmentHomeBinding? = null
    private lateinit var movieTopRatedAdapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
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
            binding?.recyclerTopRatedMovies?.apply {
                if (homeViewModel.firstLoad) delay(200)
                scrollToPosition(preferences.getPostion(PreferencesRepository.KEY_HOME, 0).first())
            }
        }
    }

    override fun onPause() {
        val manager = binding?.recyclerTopRatedMovies?.layoutManager as LinearLayoutManager
        val pos = if (manager.findFirstCompletelyVisibleItemPosition() > -1) manager.findFirstCompletelyVisibleItemPosition() else manager.findFirstVisibleItemPosition()

        /* Save last position */
        CoroutineScope(Dispatchers.IO).launch {
            preferences.updatePosition(PreferencesRepository.KEY_HOME, pos)
        }

        homeViewModel.firstLoad = false

        super.onPause()
    }

    private fun setupUI() {
        movieTopRatedAdapter = HomeAdapter(Utils.getFavoritesCallback(favoritesViewModel))

        binding?.recyclerTopRatedMovies?.apply {
            adapter = movieTopRatedAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = true

            addItemDecoration(
                MarginItemVerticalDecoration(
                    resources.getDimension(R.dimen.recyclerview_margin_horizontal).toInt(),
                    resources.getDimension(R.dimen.recyclerview_margin_horizontal).toInt()
                )
            )
        }

        binding?.swipeHome?.setOnRefreshListener {
            movieTopRatedAdapter.refresh()
            binding?.swipeHome?.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        homeViewModel.getErrorFilter().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding?.frameLayout!!, it, Snackbar.LENGTH_LONG)
                    .setAction("Retry") {
                        movieTopRatedAdapter.refresh()
                    }
                    .show()
                homeViewModel.onErrorHandled()
            }
        }

        homeViewModel.getLoadingObservable().observe(viewLifecycleOwner) {
            binding?.progressHome?.visibility = if (it == true) View.VISIBLE else View.INVISIBLE
        }

        lifecycleScope.launch {
            movieTopRatedAdapter.loadStateFlow.collectLatest { state ->
                state.setUiState(homeViewModel)
            }
        }

        lifecycleScope.launch {
            homeViewModel.topRatedMovies.collectLatest { pagingData ->
                movieTopRatedAdapter.submitData(pagingData.setFavoriteMovies(homeViewModel.favorites))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}