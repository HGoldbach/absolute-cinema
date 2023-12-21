package com.goldbach.absolutecinema.data.dto

import com.goldbach.absolutecinema.data.models.Movie
import com.google.gson.annotations.SerializedName


data class MovieDTO(
    @SerializedName("results")
    var results: List<Movie>
)
