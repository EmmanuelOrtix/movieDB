package com.example.moviedb.data.remote

import com.example.moviedb.data.model.MovieList

class MovieDataSource {

    fun getUpcomingMovies(): MovieList {
        return MovieList()
    }

    fun getTopRatedMovie(): MovieList {
        return MovieList()
    }

    fun getPopularMovie(): MovieList {
        return MovieList()
    }
}