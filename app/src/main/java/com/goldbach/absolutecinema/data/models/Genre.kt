package com.goldbach.absolutecinema.data.models

import kotlinx.serialization.SerialName

data class Genre(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)
