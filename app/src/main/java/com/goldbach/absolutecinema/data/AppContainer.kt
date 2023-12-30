package com.goldbach.absolutecinema.data

import android.content.Context
import com.goldbach.absolutecinema.data.database.MovieDatabase
import com.goldbach.absolutecinema.data.network.MovieApiService
import com.goldbach.absolutecinema.data.repositories.MovieApiRepository
import com.goldbach.absolutecinema.data.repositories.MovieDbRepository
import com.goldbach.absolutecinema.data.repositories.NetworkMovieRepository
import com.goldbach.absolutecinema.data.repositories.OfflineMovieDbRepository
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val movieApiRepository: MovieApiRepository
    val movieDbRepository: MovieDbRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    override val movieApiRepository: MovieApiRepository by lazy {
        NetworkMovieRepository(retrofitService)
    }

    override val movieDbRepository: MovieDbRepository by lazy {
        OfflineMovieDbRepository(MovieDatabase.getDatabase(context).MovieDao())
    }
}
