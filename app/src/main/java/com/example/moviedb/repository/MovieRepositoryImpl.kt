package com.example.moviedb.repository

import com.example.moviedb.data.model.MovieList
import com.example.moviedb.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource: MovieDataSource) : MovieRepository {

    override suspend fun getUpcomingMovies(): MovieList = dataSource.getUpcomingMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSource.getTopRatedMovie()

    override suspend fun getPopularMovies(): MovieList = dataSource.getPopularMovie()
}

