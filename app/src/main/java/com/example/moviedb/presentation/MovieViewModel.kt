package com.example.moviedb.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.moviedb.core.Result
import com.example.moviedb.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {

    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(
                    Triple(
                            repo.getUpcomingMovies(),
                            repo.getPopularMovies(),
                            repo.getTopRatedMovies()
                    ))
            )
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class MovieViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}