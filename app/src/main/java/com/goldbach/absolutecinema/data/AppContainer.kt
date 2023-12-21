package com.goldbach.absolutecinema.data

import com.goldbach.absolutecinema.data.network.MovieApiService
import com.goldbach.absolutecinema.data.repositories.MovieRepository
import com.goldbach.absolutecinema.data.repositories.NetworkMovieRepository
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppContainer {
    val movieRepository: MovieRepository
}

class DefaultAppContainer : AppContainer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    override val movieRepository: MovieRepository by lazy {
        NetworkMovieRepository(retrofitService)
    }
}