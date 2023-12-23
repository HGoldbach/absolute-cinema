package com.goldbach.absolutecinema.data.network

import com.goldbach.absolutecinema.data.dto.GenreDTO
import com.goldbach.absolutecinema.data.dto.MovieDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    // Movies Endpoints
    @GET("movie/upcoming")
    suspend fun getRecentlyReleasedMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieDTO>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genre: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDTO>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String
    ): Response<GenreDTO>


    // Series Endpoints
    @GET("genre/tv/list")
    suspend fun getSeriesGenres(
        @Query("api_key") apiKey: String
    ) : Response<GenreDTO>

    @GET("discover/tv")
    suspend fun getSeriesByGenre(
        @Query("with_genres") genre: Int,
        @Query("api_key") apiKey: String
    ) : Response<MovieDTO>

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") apiKey: String
    ) : Response<MovieDTO>

}