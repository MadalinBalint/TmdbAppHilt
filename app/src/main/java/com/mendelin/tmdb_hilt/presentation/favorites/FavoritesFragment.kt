package com.mendelin.tmdb_hilt.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.common.Utils.getFavoritesCallback
import com.mendelin.tmdb_hilt.databinding.FragmentFavoritesBinding
import com.mendelin.tmdb_hilt.presentation.custom_view.MarginItemVerticalDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private var binding: FragmentFavoritesBinding? = null
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        favoritesAdapter = FavoritesAdapter(favoritesViewModel.getFavoritesCallback())

        binding?.recyclerFavorites?.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = true

            addItemDecoration(
                MarginItemVerticalDecoration(
                    resources.getDimension(R.dimen.recyclerview_margin_horizontal).toInt(),
                    resources.getDimension(R.dimen.recyclerview_margin_vertical).toInt()
                )
            )
        }

        binding?.swipeHome?.setOnRefreshListener {
            favoritesViewModel.fetchFavoritesList()
            binding?.swipeHome?.isRefreshing = false
        }

        favoritesViewModel.fetchFavoritesList()
    }

    private fun observeViewModel() {
        favoritesViewModel.getErrorFilter().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding?.frameLayout!!, it, Snackbar.LENGTH_LONG)
                    .setAction("Retry") {
                        favoritesViewModel.fetchFavoritesList()
                    }
                    .show()
                favoritesViewModel.onErrorHandled()
            }
        }

        favoritesViewModel.getLoadingObservable().observe(viewLifecycleOwner) {
            binding?.progressFavorites?.visibility = if (it == true) View.VISIBLE else View.INVISIBLE
        }

        favoritesViewModel.getFavoritesList().observe(viewLifecycleOwner) { list ->
            list?.let {
                favoritesAdapter.setList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}