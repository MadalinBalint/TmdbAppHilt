package com.mendelin.tmdb_hilt.presentation.movies_popular

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
import com.mendelin.tmdb_hilt.databinding.FragmentMoviesPopularBinding
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
class MoviesPopularFragment : Fragment() {
    @Inject
    lateinit var preferences: PreferencesRepository

    private val viewModel by viewModels<MoviesPopularViewModel>()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private var binding: FragmentMoviesPopularBinding? = null
    lateinit var moviesPopularAdapter: MoviesPopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesPopularBinding.inflate(layoutInflater, container, false)
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
            binding?.recyclerPopularMovies?.apply {
                if (viewModel.firstLoad) delay(200)
                scrollToPosition(preferences.getPostion(PreferencesRepository.KEY_MOVIES_POPULAR, 0).first())
            }
        }
    }

    override fun onPause() {
        val manager = binding?.recyclerPopularMovies?.layoutManager as LinearLayoutManager
        val pos = if (manager.findFirstCompletelyVisibleItemPosition() > -1) manager.findFirstCompletelyVisibleItemPosition() else manager.findFirstVisibleItemPosition()

        /* Save last position */
        CoroutineScope(Dispatchers.IO).launch {
            preferences.updatePosition(PreferencesRepository.KEY_MOVIES_POPULAR, pos)
        }

        viewModel.firstLoad = false

        super.onPause()
    }

    private fun setupUI() {
        moviesPopularAdapter = MoviesPopularAdapter(favoritesViewModel.getFavoritesCallback())

        binding?.recyclerPopularMovies?.apply {
            adapter = moviesPopularAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = true

            addItemDecoration(
                MarginItemVerticalDecoration(
                    resources.getDimension(R.dimen.recyclerview_margin_horizontal).toInt(),
                    resources.getDimension(R.dimen.recyclerview_margin_vertical).toInt()
                )
            )
        }

        binding?.swipePopularMovies?.setOnRefreshListener {
            moviesPopularAdapter.refresh()
            binding?.swipePopularMovies?.isRefreshing = false
        }

        viewModel.fetchPopularMovies()
    }

    private fun observeViewModel() {
        viewModel.getErrorFilter().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding?.frameLayout!!, it, Snackbar.LENGTH_LONG)
                    .setAction("Retry") {
                        moviesPopularAdapter.refresh()
                    }
                    .show()
                viewModel.onErrorHandled()
            }
        }

        viewModel.getLoadingObservable().observe(viewLifecycleOwner) {
            binding?.progressPopularMovies?.visibility = if (it == true) View.VISIBLE else View.INVISIBLE
        }

        lifecycleScope.launch {
            moviesPopularAdapter.loadStateFlow.collectLatest { state ->
                viewModel.setUiState(state)
            }
        }

        viewModel.popularMovies.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                moviesPopularAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}