package com.goldbach.absolutecinema.data.dto

import com.goldbach.absolutecinema.data.models.Genre
import kotlinx.serialization.SerialName

data class GenreDTO(
    @SerialName("genres")
    val genres: List<Genre>
)
