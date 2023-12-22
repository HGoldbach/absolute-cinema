package com.goldbach.absolutecinema.data.network

import com.goldbach.absolutecinema.data.dto.GenreDTO
import com.goldbach.absolutecinema.data.dto.MovieDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieDTO>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String
    ): Response<GenreDTO>
}