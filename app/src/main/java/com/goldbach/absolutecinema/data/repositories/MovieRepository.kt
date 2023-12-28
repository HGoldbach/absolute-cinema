package com.goldbach.absolutecinema.data.repositories

import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.dto.GenreDTO
import com.goldbach.absolutecinema.data.dto.MovieDTO
import com.goldbach.absolutecinema.data.network.MovieApiService
import retrofit2.Response

interface MovieRepository {
    suspend fun getRecentlyReleasedMovies(): Response<MovieDTO>
    suspend fun getMoviesByGenre(id: Int): Response<MovieDTO>
    suspend fun getPopularSeries(): Response<MovieDTO>
    suspend fun getSeriesByGenre(id: Int): Response<MovieDTO>
    suspend fun getGenres(type: String): Response<GenreDTO>
    suspend fun getMoviesAndSeriesByTitle(title: String) : Response<MovieDTO>

}

class NetworkMovieRepository(
    private val movieApiService: MovieApiService
) : MovieRepository {
    override suspend fun getRecentlyReleasedMovies(): Response<MovieDTO> {
        return movieApiService.getRecentlyReleasedMovies(Constants.API_KEY)
    }

    override suspend fun getMoviesByGenre(id: Int): Response<MovieDTO> {
        return movieApiService.getMoviesByGenre(id, Constants.API_KEY)
    }

    override suspend fun getPopularSeries(): Response<MovieDTO> {
        return movieApiService.getPopularSeries(Constants.API_KEY)
    }

    override suspend fun getSeriesByGenre(id: Int): Response<MovieDTO> {
        return movieApiService.getSeriesByGenre(id, Constants.API_KEY)
    }

    override suspend fun getGenres(type: String): Response<GenreDTO> {
        return if (type == "Movie") movieApiService.getMovieGenres(Constants.API_KEY)
        else movieApiService.getSeriesGenres(Constants.API_KEY)
    }

    override suspend fun getMoviesAndSeriesByTitle(title: String): Response<MovieDTO> {
        return movieApiService.searchMoviesAndSeries(title, Constants.API_KEY)
    }
}