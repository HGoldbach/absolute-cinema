package com.goldbach.absolutecinema.data.repositories

import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.dto.GenreResponse
import com.goldbach.absolutecinema.data.dto.MovieResponse
import com.goldbach.absolutecinema.data.network.MovieApiService
import retrofit2.Response

interface MovieApiRepository {
    suspend fun getRecentlyReleasedMovies(): Response<MovieResponse>
    suspend fun getMoviesByGenre(id: Int): Response<MovieResponse>
    suspend fun getPopularSeries(): Response<MovieResponse>
    suspend fun getSeriesByGenre(id: Int): Response<MovieResponse>
    suspend fun getGenres(type: String): Response<GenreResponse>
    suspend fun getMoviesAndSeriesByTitle(title: String) : Response<MovieResponse>

}

class NetworkMovieRepository(
    private val movieApiService: MovieApiService
) : MovieApiRepository {
    override suspend fun getRecentlyReleasedMovies(): Response<MovieResponse> {
        return movieApiService.getRecentlyReleasedMovies(Constants.API_KEY)
    }

    override suspend fun getMoviesByGenre(id: Int): Response<MovieResponse> {
        return movieApiService.getMoviesByGenre(id, Constants.API_KEY)
    }

    override suspend fun getPopularSeries(): Response<MovieResponse> {
        return movieApiService.getPopularSeries(Constants.API_KEY)
    }

    override suspend fun getSeriesByGenre(id: Int): Response<MovieResponse> {
        return movieApiService.getSeriesByGenre(id, Constants.API_KEY)
    }

    override suspend fun getGenres(type: String): Response<GenreResponse> {
        return if (type == "Movie") movieApiService.getMovieGenres(Constants.API_KEY)
        else movieApiService.getSeriesGenres(Constants.API_KEY)
    }

    override suspend fun getMoviesAndSeriesByTitle(title: String): Response<MovieResponse> {
        return movieApiService.searchMoviesAndSeries(title, Constants.API_KEY)
    }
}