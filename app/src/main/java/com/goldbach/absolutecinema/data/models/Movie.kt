package com.goldbach.absolutecinema.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Movie", indices = [Index(value = ["title"], unique = true)])
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("poster")
    val poster: String,
    @ColumnInfo("release_date")
    val releaseDate: String,
    @ColumnInfo("favorite")
    var isFavorite: Boolean = false
)
