package com.goldbach.absolutecinema.data.dto

import com.google.gson.annotations.SerializedName


data class MovieResponse(
    @SerializedName("results")
    var results: List<MovieDto>
)
