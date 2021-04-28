package com.example.moviedb.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.moviedb.R
import com.example.moviedb.core.Result
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.MovieDataSource
import com.example.moviedb.databinding.FragmentMovieBinding
import com.example.moviedb.presentation.MovieViewModel
import com.example.moviedb.presentation.MovieViewModelFactory
import com.example.moviedb.repository.MovieRepositoryImpl
import com.example.moviedb.repository.RetrofitClient
import com.example.moviedb.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.moviedb.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.moviedb.ui.movie.adapters.concat.UpComingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
                MovieRepositoryImpl(
                        MovieDataSource(RetrofitClient.webService)
                )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0, UpComingConcatAdapter(MovieAdapter(result.data.first.results, this@MovieFragment)))
                        addAdapter(1, TopRatedConcatAdapter(MovieAdapter(result.data.second.results, this@MovieFragment)))
                        addAdapter(2, PopularConcatAdapter(MovieAdapter(result.data.third.results, this@MovieFragment)))
                    }

                    binding.rvMovie.adapter = concatAdapter
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Error", "${result.exception}")
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
                movie.poster_path,
                movie.backdrop_path,
                movie.vote_average.toFloat(),
                movie.vote_count,
                movie.overview,
                movie.title,
                movie.original_language,
                movie.release_date)

        findNavController().navigate(action)
    }
}