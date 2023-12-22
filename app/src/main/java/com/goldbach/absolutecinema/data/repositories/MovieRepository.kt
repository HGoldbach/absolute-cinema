package com.goldbach.absolutecinema.data.repositories

import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.dto.GenreDTO
import com.goldbach.absolutecinema.data.dto.MovieDTO
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.data.network.MovieApiService
import retrofit2.Response

interface MovieRepository {
    suspend fun getMovies(): Response<MovieDTO>
    suspend fun getGenres(): Response<GenreDTO>
}

class NetworkMovieRepository(
    private val movieApiService: MovieApiService
) : MovieRepository {
    override suspend fun getMovies(): Response<MovieDTO> {
        return movieApiService.getMovies(Constants.API_KEY)
    }

    override suspend fun getGenres(): Response<GenreDTO> {
        return movieApiService.getGenres(Constants.API_KEY)
    }
}