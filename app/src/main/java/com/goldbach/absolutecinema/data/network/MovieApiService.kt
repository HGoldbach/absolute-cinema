package com.goldbach.absolutecinema.data.network

import com.goldbach.absolutecinema.data.dto.GenreResponse
import com.goldbach.absolutecinema.data.dto.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    // Movies Endpoints
    @GET("movie/upcoming")
    suspend fun getRecentlyReleasedMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genre: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String
    ): Response<GenreResponse>


    // Series Endpoints
    @GET("genre/tv/list")
    suspend fun getSeriesGenres(
        @Query("api_key") apiKey: String
    ) : Response<GenreResponse>

    @GET("discover/tv")
    suspend fun getSeriesByGenre(
        @Query("with_genres") genre: Int,
        @Query("api_key") apiKey: String
    ) : Response<MovieResponse>

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") apiKey: String
    ) : Response<MovieResponse>

    // Search
    @GET("search/multi")
    suspend fun searchMoviesAndSeries(
        @Query("query") title: String,
        @Query("api_key") apiKey: String
    ) : Response<MovieResponse>
}