package com.mendelin.tmdb_hilt.presentation.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.databinding.FragmentMovieDetailsBinding
import com.mendelin.tmdb_hilt.presentation.custom_view.MarginItemHorizontalDecoration
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel by viewModels<MoviesDetailsViewModel>()

    private var binding: FragmentMovieDetailsBinding? = null
    private lateinit var movieCastAdapter: CastAdapter
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        binding?.viewmodel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieCastAdapter = CastAdapter()

        binding?.recyclerMovieCast?.apply {
            adapter = movieCastAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            isNestedScrollingEnabled = true
            setHasFixedSize(true)

            addItemDecoration(
                MarginItemHorizontalDecoration(
                    resources.getDimension(R.dimen.recyclerview_margin_vertical).toInt(),
                    resources.getDimension(R.dimen.recyclerview_margin_vertical).toInt()
                )
            )
        }

        Timber.d("Movie ID = ${args.movieId}")

        with(viewModel) {
            fetchMovieCredits(args.movieId)
            fetchMovieDetails(args.movieId)
        }

        viewModel.casting.observe(viewLifecycleOwner) {
            Timber.d(it.toString())

            movieCastAdapter.setList(it.sortedBy { person -> person.order })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}