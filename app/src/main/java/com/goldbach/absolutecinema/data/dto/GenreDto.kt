package com.goldbach.absolutecinema.data.dto

import kotlinx.serialization.SerialName

data class GenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)
