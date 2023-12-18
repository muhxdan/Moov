package com.salt.apps.moov.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moov_entity")
data class MoovEntity(
    @PrimaryKey
    var id: Int,
    val overview: String? = null,
    var backdropPath: String? = null,
    var posterPath: String? = null,
    var originalLanguage: String,
    var releaseDate: String? = null,
    var voteAverage: Double? = null,
    var title: String,
    var isFavorite: Boolean = false
)