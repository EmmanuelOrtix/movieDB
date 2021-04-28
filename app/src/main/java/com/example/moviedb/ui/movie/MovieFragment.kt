package com.example.moviedb.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moviedb.R
import com.example.moviedb.core.Result
import com.example.moviedb.data.remote.MovieDataSource
import com.example.moviedb.databinding.FragmentMovieBinding
import com.example.moviedb.presentation.MovieViewModel
import com.example.moviedb.presentation.MovieViewModelFactory
import com.example.moviedb.repository.MovieRepositoryImpl
import com.example.moviedb.repository.RetrofitClient

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webService)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        viewModel.fetchUpcomingMovies().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {
                    Log.d("Livedata", "Loading")
                }
                is Result.Success -> {
                    Log.d("Livedata", "${result.data}")
                }
                is Result.Failure -> {
                    Log.d("Error", "${result.exception}")
                }
            }
        })

        viewModel.fetchPopularMovies().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {
                    Log.d("Livedata", "Loading")
                }
                is Result.Success -> {
                    Log.d("Livedata", "${result.data}")
                }
                is Result.Failure -> {
                    Log.d("Error", "${result.exception}")
                }
            }
        })

        viewModel.fetchTopRatedMovies().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {
                    Log.d("Livedata", "Loading")
                }
                is Result.Success -> {
                    Log.d("Livedata", "${result.data}")
                }
                is Result.Failure -> {
                    Log.d("Error", "${result.exception}")
                }
            }
        })

    }
}