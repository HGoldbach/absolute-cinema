package com.goldbach.absolutecinema.data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    var id: String,
    @SerializedName("original_title", alternate = ["original_name"])
    var title: String,
    @SerializedName("overview")
    var description: String,
    @SerializedName("poster_path")
    var poster: String,
    @SerializedName("release_date", alternate = ["first_air_date"])
    var releaseDate: String
)