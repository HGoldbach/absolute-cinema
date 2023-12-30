package com.goldbach.absolutecinema.data.dto

import kotlinx.serialization.SerialName

data class GenreResponse(
    @SerialName("genres")
    val genres: List<GenreDto>
)
