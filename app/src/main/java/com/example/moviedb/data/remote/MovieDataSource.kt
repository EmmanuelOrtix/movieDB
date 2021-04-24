package com.example.moviedb.data.remote

import com.example.moviedb.application.AppConstants
import com.example.moviedb.data.model.MovieList
import com.example.moviedb.repository.WebService

class MovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovie(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovie(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}