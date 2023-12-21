package com.goldbach.absolutecinema.data.network

import com.goldbach.absolutecinema.data.dto.MovieDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieDTO>
}